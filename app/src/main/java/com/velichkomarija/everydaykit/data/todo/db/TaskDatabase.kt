package com.velichkomarija.everydaykit.data.todo.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TaskEntity::class], version = 1, exportSchema = true)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao
}