package com.velichkomarija.everydaykit.data.todo

import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getTasksFlow(): Flow<List<Task>>
    suspend fun loadTasksFromRemote()
    fun getTaskStream(taskId: String): Flow<Task?>
    suspend fun getTask(taskId: String): Task?
    suspend fun createTask(title: String, description: String): String
    suspend fun updateTask(taskId: String, title: String, description: String)
    suspend fun completeTask(taskId: String) : Boolean
    suspend fun activateTask(taskId: String): Boolean
    suspend fun clearCompletedTasks()
    suspend fun deleteAllTasks()
    suspend fun deleteTask(taskId: String)
    suspend fun saveTasksRemote(): Boolean
}