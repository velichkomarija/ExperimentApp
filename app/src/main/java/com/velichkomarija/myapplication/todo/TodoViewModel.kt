package com.velichkomarija.myapplication.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    val uiState: StateFlow<TodoTasksUIState> = combine(
        _items, _isLoading
    )
    { items, isLoading ->
        if (isLoading) {
            TodoTasksUIState(isLoading = true)
        }
        // todo if (items.isNotEmpty()) {
        TodoTasksUIState(isLoading = false, items = items)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = TodoTasksUIState(isLoading = true)
    )

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