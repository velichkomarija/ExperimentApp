package com.velichkomarija.myapplication.todo

import com.velichkomarija.myapplication.data.todo.Task

data class TodoTasksUIState(
    val items: List<Task> = emptyList(),
    val isLoading: Boolean = false,
)