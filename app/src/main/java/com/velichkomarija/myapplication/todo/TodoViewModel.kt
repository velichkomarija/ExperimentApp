package com.velichkomarija.myapplication.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.velichkomarija.myapplication.ADD_EDIT_RESULT_OK
import com.velichkomarija.myapplication.DELETE_RESULT_OK
import com.velichkomarija.myapplication.EDIT_RESULT_OK
import com.velichkomarija.myapplication.R
import com.velichkomarija.myapplication.data.todo.LocalTaskRepository
import com.velichkomarija.myapplication.data.todo.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val localTaskRepository: LocalTaskRepository,
    // private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    private val _items = localTaskRepository.getTasksFlow()
    private val _userMessage: MutableStateFlow<Int?> = MutableStateFlow(null)

    val uiState: StateFlow<TodoTasksUIState> = combine(
        _items, _isLoading, _userMessage
    )
    { items, isLoading, userMessage ->
        if (isLoading) {
            TodoTasksUIState(isLoading = true)
        }
        // todo if (items.isNotEmpty()) {
        TodoTasksUIState(isLoading = false, items = items, userMessage = userMessage)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = TodoTasksUIState(isLoading = true)
    )

    fun snackbarMessageShown() {
        _userMessage.value = null
    }

    private fun showSnackbarMessage(message: Int) {
        _userMessage.value = message
    }

    fun showEditResultMessage(result: Int) {
        when (result) {
            EDIT_RESULT_OK -> showSnackbarMessage(R.string.successfully_saved_task_message)
            ADD_EDIT_RESULT_OK -> showSnackbarMessage(R.string.successfully_added_task_message)
            DELETE_RESULT_OK -> showSnackbarMessage(R.string.successfully_deleted_task_message)
        }
    }

        fun completeTask(task: Task, completed: Boolean) = viewModelScope.launch {
        if (completed) {
            localTaskRepository.completeTask(task.id)
            // showSnackbar
        } else {
            localTaskRepository.activateTask(task.id)
            // showSnackbar
        }
    }

}