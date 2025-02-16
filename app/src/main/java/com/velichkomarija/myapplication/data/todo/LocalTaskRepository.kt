package com.velichkomarija.myapplication.data.todo

import com.velichkomarija.myapplication.DefaultDispatcher
import com.velichkomarija.myapplication.data.toEntity
import com.velichkomarija.myapplication.data.toExternal
import com.velichkomarija.myapplication.data.todo.db.TaskDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class LocalTaskRepository @Inject constructor(
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
    private val localDataSource: TaskDao,
): TaskRepository {

    override fun getTasksFlow(): Flow<List<Task>> {
        return localDataSource.observeAll().map { tasks ->
            withContext(dispatcher) {
                listOf(
                    Task(
                        title = "Title 1",
                        description = "Description 1",
                        isCompleted = false,
                        id = "ID 1"
                    ),
                    Task(
                        title = "Title 2",
                        description = "Description 2",
                        isCompleted = true,
                        id = "ID 2"
                    ),
                    Task(
                        title = "Title 3",
                        description = "Description 3",
                        isCompleted = true,
                        id = "ID 3"
                    ),
                    Task(
                        title = "Title 4",
                        description = "Description 4",
                        isCompleted = false,
                        id = "ID 4"
                    ),
                    Task(
                        title = "Title 5",
                        description = "Description 5",
                        isCompleted = true,
                        id = "ID 5"
                    ),
                )
            }
        }
    }

    override suspend fun getTasks(): List<Task> {
        return withContext(dispatcher) {
            localDataSource.getAll().toExternal()
        }
    }

    override fun getTaskStream(taskId: String): Flow<Task?> {
        return localDataSource.observeById(taskId).map { it.toExternal() }
    }

    override suspend fun getTask(taskId: String): Task? {
        return localDataSource.getById(taskId)?.toExternal()
    }

    override suspend fun createTask(title: String, description: String): String {
        val taskId = withContext(dispatcher) {
            UUID.randomUUID().toString()
        }
        val task = Task(
            title = title,
            description = description,
            id = taskId,
        )
        localDataSource.upsert(task.toEntity())
        return taskId
    }

    override suspend fun updateTask(taskId: String, title: String, description: String) {
        val task = getTask(taskId)?.copy(
            title = title,
            description = description
        ) ?: throw Exception("Task (id $taskId) not found")
        localDataSource.upsert(task.toEntity())
    }

    override suspend fun completeTask(taskId: String) {
        localDataSource.updateCompleted(taskId = taskId, completed = true)
    }

    override suspend fun activateTask(taskId: String) {
        localDataSource.updateCompleted(taskId = taskId, completed = false)
    }

    override suspend fun clearCompletedTasks() {
        localDataSource.deleteCompleted()
    }

    override suspend fun deleteAllTasks() {
        localDataSource.deleteAll()
    }

    override suspend fun deleteTask(taskId: String) {
        localDataSource.deleteById(taskId)
    }


}