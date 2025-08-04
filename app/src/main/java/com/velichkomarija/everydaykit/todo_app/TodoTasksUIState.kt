package com.velichkomarija.everydaykit.todo_app

import com.velichkomarija.everydaykit.R
import com.velichkomarija.everydaykit.data.todo.Task

data class TodoTasksUIState(
    val items: List<Task> = emptyList(),
    val isLoading: Boolean = false,
    val userMessage: Int? = null,
    val filteringUiInfo: FilteringUiInfo = FilteringUiInfo(),
)

data class FilteringUiInfo(
    val currentFilteringLabel: Int = R.string.label_all,
    val noTasksLabel: Int = R.string.no_tasks_all,
    val noTaskIconRes: Int = R.drawable.ic_no_task_96,
)