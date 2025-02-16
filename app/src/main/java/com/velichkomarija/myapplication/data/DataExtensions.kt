package com.velichkomarija.myapplication.data

import com.velichkomarija.myapplication.data.todo.Task
import com.velichkomarija.myapplication.data.todo.db.TaskEntity

fun Task.toEntity() = TaskEntity(
    id = id,
    title = title,
    description = description,
    isCompleted = isCompleted
)

fun TaskEntity.toExternal() = Task(
    id = id,
    title = title,
    description = description,
    isCompleted = isCompleted,
)

fun List<TaskEntity>.toExternal() = map(TaskEntity::toExternal)