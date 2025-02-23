package com.velichkomarija.everydaykit.todo

import com.velichkomarija.everydaykit.data.todo.Task

data class TodoTasksUIState(
    val items: List<Task> = emptyList(),
    val isLoading: Boolean = false,
    val userMessage: Int? = null
)