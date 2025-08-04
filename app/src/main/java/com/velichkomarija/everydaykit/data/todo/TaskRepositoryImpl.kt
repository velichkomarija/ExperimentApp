package com.velichkomarija.everydaykit.data.todo

import com.velichkomarija.everydaykit.ApplicationScope
import com.velichkomarija.everydaykit.DefaultDispatcher
import com.velichkomarija.everydaykit.data.toEntity
import com.velichkomarija.everydaykit.data.toExternal
import com.velichkomarija.everydaykit.data.todo.db.TaskDao
import com.velichkomarija.everydaykit.data.todo.network.NetworkSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
    @ApplicationScope private val scope: CoroutineScope,
    private val localDataSource: TaskDao,
    private val networkSource: NetworkSource,
) : TaskRepository {

    override fun getTasksFlow(): Flow<List<Task>> {
        return localDataSource.observeAll().map { tasks ->
            withContext(dispatcher) {
                tasks.toExternal()
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
        networkSource.saveOrUpdateTask(task)
        return taskId
    }

    override suspend fun updateTask(taskId: String, title: String, description: String) {
        val task = getTask(taskId)?.copy(
            title = title,
            description = description
        ) ?: throw Exception("Task (id $taskId) not found")
        localDataSource.upsert(task.toEntity())
        networkSource.saveOrUpdateTask(task)
    }

    override suspend fun completeTask(taskId: String) : Boolean {
        localDataSource.updateCompleted(taskId = taskId, completed = true)
       return networkSource.updateCompleted(taskId = taskId, completed = true)
    }

    override suspend fun activateTask(taskId: String) : Boolean {
        localDataSource.updateCompleted(taskId = taskId, completed = false)
         return networkSource.updateCompleted(taskId = taskId, completed = false)
    }

    override suspend fun clearCompletedTasks() {
        localDataSource.deleteCompleted()
        // todo remote delete
    }

    override suspend fun deleteAllTasks() {
        localDataSource.deleteAll()
        // todo remote delete
    }

    override suspend fun deleteTask(taskId: String) {
        localDataSource.deleteById(taskId)
        networkSource.deleteTask(taskId)
    }

    override suspend fun saveTasksRemote(): Boolean {
        return withContext(dispatcher)  {
            val localTask = localDataSource.getAll().toExternal()
            networkSource.saveTasks(localTask)
        }
    }
}