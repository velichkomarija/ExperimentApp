package com.velichkomarija.everydaykit.todo.addnew

data class AddOrUpdateTaskUIState(
    val title: String = "",
    val description: String = "",
    val userMessage: Int? = null,
    val isCompleted: Boolean = false,
    val isLoading: Boolean = false,
    val isSaved: Boolean = false,
    val isDeleted: Boolean = false
)