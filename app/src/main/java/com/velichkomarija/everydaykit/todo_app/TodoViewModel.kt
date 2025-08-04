package com.velichkomarija.everydaykit.todo_app

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.velichkomarija.everydaykit.ACTIVATE_RESULT_OK
import com.velichkomarija.everydaykit.ADD_EDIT_RESULT_OK
import com.velichkomarija.everydaykit.COMPLETE_RESULT_OK
import com.velichkomarija.everydaykit.DELETE_RESULT_OK
import com.velichkomarija.everydaykit.EDIT_RESULT_OK
import com.velichkomarija.everydaykit.R
import com.velichkomarija.everydaykit.data.todo.TaskRepositoryImpl
import com.velichkomarija.everydaykit.data.todo.Task
import com.velichkomarija.everydaykit.todo.TaskFilterType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val taskRepository: TaskRepositoryImpl,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _savedFilterType =
        savedStateHandle.getStateFlow(TASKS_FILTER_SAVED_STATE_KEY, TaskFilterType.ALL_TASKS)

    private val _filterUiInfo = _savedFilterType.map { getFilterUiInfo(it) }.distinctUntilChanged()
    private val _isLoading = MutableStateFlow(false)
    private val _userMessage: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val _filteredTasksAsync =
        combine(taskRepository.getTasksFlow(), _savedFilterType) { tasks, type ->
            filterTasks(tasks, type)
        }
            .map { Async.Success(it) }
            .catch<Async<List<Task>>> { emit(Async.Error(R.string.loading_tasks_error)) }

    val uiState: StateFlow<TodoTasksUIState> = combine(
        _filterUiInfo, _isLoading, _userMessage, _filteredTasksAsync
    )
    { filterUiInfo, isLoading, userMessage, filteredTasksAsync ->
        when (filteredTasksAsync) {
            Async.Loading -> {
                TodoTasksUIState(isLoading = true)
            }

            is Async.Error -> {
                TodoTasksUIState(userMessage = filteredTasksAsync.errorMessage)
            }

            is Async.Success -> {
                TodoTasksUIState(
                    items = filteredTasksAsync.data,
                    filteringUiInfo = filterUiInfo,
                    isLoading = !taskRepository.saveTasksRemote(),
                    userMessage = userMessage
                )
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = TodoTasksUIState(isLoading = true)
    )

    fun snackbarMessageShown() {
        _userMessage.value = null
    }

    fun setFiltering(requestType: TaskFilterType) {
        savedStateHandle[TASKS_FILTER_SAVED_STATE_KEY] = requestType
    }

    fun showEditResultMessage(result: Int) {
        when (result) {
            EDIT_RESULT_OK -> showSnackbarMessage(R.string.successfully_saved_task_message)
            ADD_EDIT_RESULT_OK -> showSnackbarMessage(R.string.successfully_added_task_message)
            DELETE_RESULT_OK -> showSnackbarMessage(R.string.successfully_deleted_task_message)
            COMPLETE_RESULT_OK -> showSnackbarMessage(R.string.successfully_completed_task_message)
            ACTIVATE_RESULT_OK -> showSnackbarMessage(R.string.successfully_activated_task_message)
        }
    }

    fun completeTask(task: Task, completed: Boolean) = viewModelScope.launch {
        if (completed) {
            val result = taskRepository.completeTask(task.id)
            if (result) {
                showEditResultMessage(COMPLETE_RESULT_OK)
            }
        } else {
            val result = taskRepository.activateTask(task.id)
            if (result) {
                showEditResultMessage(ACTIVATE_RESULT_OK)
            }
        }
    }

    private fun showSnackbarMessage(message: Int) {
        _userMessage.value = message
    }

    private fun filterTasks(tasks: List<Task>, filteringType: TaskFilterType): List<Task> {
        val tasksToShow = ArrayList<Task>()
        for (task in tasks) {
            when (filteringType) {
                TaskFilterType.ALL_TASKS -> tasksToShow.add(task)
                TaskFilterType.ACTIVE_TASKS -> if (!task.isCompleted) {
                    tasksToShow.add(task)
                }

                TaskFilterType.COMPLETED_TASKS -> if (task.isCompleted) {
                    tasksToShow.add(task)
                }
            }
        }
        return tasksToShow
    }

    private fun getFilterUiInfo(requestType: TaskFilterType): FilteringUiInfo =
        when (requestType) {
            TaskFilterType.ALL_TASKS -> {
                FilteringUiInfo(
                    R.string.label_all,
                    R.string.no_tasks_all,
                    R.drawable.ic_no_task_96
                )
            }

            TaskFilterType.ACTIVE_TASKS -> {
                FilteringUiInfo(
                    R.string.label_active,
                    R.string.no_tasks_active,
                    R.drawable.ic_active_task_96
                )
            }

            TaskFilterType.COMPLETED_TASKS -> {
                FilteringUiInfo(
                    R.string.label_completed,
                    R.string.no_tasks_completed,
                    R.drawable.ic_done_all_96
                )
            }
        }

    companion object {
        const val TASKS_FILTER_SAVED_STATE_KEY = "TASKS_FILTER_SAVED_STATE_KEY"
    }
}

sealed class Async<out T> {
    data object Loading : Async<Nothing>()

    data class Error(val errorMessage: Int) : Async<Nothing>()

    data class Success<out T>(val data: T) : Async<T>()
}