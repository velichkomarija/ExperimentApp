package com.velichkomarija.everydaykit.data.functions

import android.content.Context
import com.velichkomarija.everydaykit.R
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LocalFunctionsDataRepository(@ApplicationContext context: Context) : FunctionsDataRepository {
    private val example = listOf(
        FunctionData(context.getString(R.string.todo_list_app), null, "TodoList"),
    )
    override val functions: Flow<List<FunctionData>> = flow {
        emit(example)
    }
}