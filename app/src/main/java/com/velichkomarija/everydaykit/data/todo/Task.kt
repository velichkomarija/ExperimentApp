package com.velichkomarija.everydaykit.data.todo

import androidx.annotation.Keep


@Keep
data class Task(
    val id: String,
    val title: String,
    val description: String,
    val isCompleted: Boolean = false,
) {
    val titleForList: String
        get() = title.ifEmpty { "Title" }

    constructor() : this("", "Title", "", false)
}