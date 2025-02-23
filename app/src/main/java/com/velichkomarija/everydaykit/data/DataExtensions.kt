package com.velichkomarija.everydaykit.data

import com.velichkomarija.everydaykit.data.todo.Task
import com.velichkomarija.everydaykit.data.todo.db.TaskEntity

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