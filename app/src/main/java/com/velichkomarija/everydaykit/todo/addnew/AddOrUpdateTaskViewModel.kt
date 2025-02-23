package com.velichkomarija.everydaykit.todo.addnew

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.velichkomarija.everydaykit.R
import com.velichkomarija.everydaykit.TodoDestinationArgs
import com.velichkomarija.everydaykit.data.todo.LocalTaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddOrUpdateTaskViewModel @Inject constructor(
    private val localTaskRepository: LocalTaskRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val taskId: String? = savedStateHandle[TodoDestinationArgs.TASK_ID_ARG]

    private val _uiState = MutableStateFlow(AddOrUpdateTaskUIState())
    val uiState: StateFlow<AddOrUpdateTaskUIState> = _uiState.asStateFlow()

    init {
        if (taskId != null) {
            loadTask(taskId)
        }
    }

    fun snackbarMessageShown() {
        _uiState.update {
            it.copy(userMessage = null)
        }
    }

    fun updateTitle(newTitle: String) {
        _uiState.update {
            it.copy(title = newTitle)
        }
    }

    fun deleteTask() = viewModelScope.launch {
        if (taskId != null) {
            localTaskRepository.deleteTask(taskId)
            _uiState.update {
                it.copy(isDeleted = true)
            }
        }
    }


    fun updateDescription(newDescription: String) {
        _uiState.update {
            it.copy(description = newDescription)
        }
    }

    fun saveTask() {
        if (uiState.value.title.isEmpty() || uiState.value.description.isEmpty()) {
            _uiState.update {
                it.copy(userMessage = R.string.empty_task_message)
            }
            return
        }

        if (taskId == null) {
            createNewTask()
        } else {
            updateTask()
        }
    }

    private fun createNewTask() = viewModelScope.launch {
        localTaskRepository.createTask(uiState.value.title, uiState.value.description)
        _uiState.update {
            it.copy(isSaved = true)
        }
    }

    private fun updateTask() {
        if (taskId == null) {
            throw RuntimeException("updateTask() was called but task is new.")
        }
        viewModelScope.launch {
            localTaskRepository.updateTask(
                taskId,
                title = uiState.value.title,
                description = uiState.value.description,
            )
        }
        _uiState.update {
            it.copy(isSaved = true)
        }
    }


    private fun loadTask(taskId: String) {
        _uiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            localTaskRepository.getTask(taskId).let { task ->
                if (task != null) {
                    _uiState.update {
                        it.copy(
                            title = task.title,
                            description = task.description,
                            isCompleted = task.isCompleted,
                            isLoading = false
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                }
            }
        }
    }
}