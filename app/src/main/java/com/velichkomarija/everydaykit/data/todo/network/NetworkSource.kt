package com.velichkomarija.everydaykit.data.todo.network

import com.velichkomarija.everydaykit.data.todo.Task

interface NetworkSource {
    fun loadTasks(): List<Task>?
    fun saveTasks(tasks: List<Task>): Boolean
    fun saveOrUpdateTask(task: Task): Boolean
    fun deleteTask(taskId: String): Boolean
    fun updateCompleted(taskId: String, completed: Boolean): Boolean
}