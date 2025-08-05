package com.velichkomarija.everydaykit.data.todo.network

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.velichkomarija.everydaykit.AuthService
import com.velichkomarija.everydaykit.data.todo.Task

//todo всякие ошибки на auth
class FirestoreDataSource(private val authService: AuthService) : NetworkSource {

    private val tag = this.javaClass.name
    private val firestore: FirebaseFirestore = Firebase.firestore

    override fun loadTasks(): List<Task>? {
        var tasks: List<Task>? = null
        firestore
            .collection(COLLECTION_USER)
            .document(authService.getUserUid())
            .collection(COLLECTION_TASKS).get()
            .addOnSuccessListener { snapshot ->
                tasks = snapshot.toObjects(Task::class.java)
                Log.d(tag, "Tasks loaded")
            }
            .addOnFailureListener { exception ->
                Log.e(tag, "Tasks not loaded because ${exception.localizedMessage}")
            }
        return tasks
    }

    override fun saveTasks(tasks: List<Task>): Boolean {
        var resultFlag = false
        val batch = firestore.batch()
        val tasksRef = firestore
            .collection(COLLECTION_USER)
            .document(authService.getUserUid())
            .collection(COLLECTION_TASKS)

        for (task in tasks) {
            val docRef = tasksRef.document(task.id)
            batch.set(
                docRef, mapOf(
                    TITLE to task.title,
                    DESCRIPTION to task.description,
                    IS_COMPLETED to task.isCompleted
                )
            )
        }
        batch.commit()
            .addOnSuccessListener {
                resultFlag = true
                Log.d(tag, "Tasks saved")
            }
            .addOnFailureListener { exception ->
                Log.d(tag, "Tasks were not saved because ${exception.localizedMessage}")
            }
        return resultFlag
    }


    override fun saveOrUpdateTask(task: Task): Boolean {
        var resultFlag = false
        val taskRef = firestore
            .collection(COLLECTION_USER)
            .document(authService.getUserUid())
            .collection(COLLECTION_TASKS)
            .document(task.id)
        taskRef.set(
            mapOf(
                TITLE to task.title,
                DESCRIPTION to task.description,
                IS_COMPLETED to task.isCompleted
            )
        )
            .addOnSuccessListener {
                resultFlag = true
                Log.d(tag, "Task with taskId=${task.id} was saved")
            }.addOnFailureListener { exception ->
                Log.d(tag, "Task was not saved ${exception.localizedMessage}")
            }
        return resultFlag
    }

    override fun updateCompleted(taskId: String, completed: Boolean): Boolean {
        var resultFlag = false
        val taskRef = firestore
            .collection(COLLECTION_USER)
            .document(authService.getUserUid())
            .collection(COLLECTION_TASKS)
            .document(taskId)
        taskRef.update(mapOf(IS_COMPLETED to completed))
            .addOnSuccessListener {
                resultFlag = true
                Log.d(tag, "Task with taskId=$taskId was completed =$completed)")
            }.addOnFailureListener { exception ->
                Log.d(tag, "Task completed state was not saved ${exception.localizedMessage}")
            }
        return resultFlag
    }

    override fun deleteTask(taskId: String): Boolean {
        var resultFlag = false
        firestore
            .collection(COLLECTION_USER)
            .document(authService.getUserUid())
            .collection(COLLECTION_TASKS)
            .document(taskId).delete()
            .addOnSuccessListener {
                resultFlag = true
                Log.d(tag, "Task with taskId=$taskId deleted")
            }
            .addOnFailureListener { exception ->
                Log.d(tag, "Task was not deleted ${exception.localizedMessage}")
            }
        return resultFlag
    }

    companion object {
        private const val COLLECTION_USER = "users"
        private const val COLLECTION_TASKS = "tasks"
        private const val TITLE = "title"
        private const val DESCRIPTION = "description"
        private const val IS_COMPLETED = "completed"
    }
}
