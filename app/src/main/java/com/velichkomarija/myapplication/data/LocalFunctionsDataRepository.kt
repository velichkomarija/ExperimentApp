package com.velichkomarija.myapplication.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class LocalFunctionsDataRepository : FunctionsDataRepository {
    private val example = listOf(
        FunctionData("Todo list", null, "Todo list"),
        FunctionData("Todo list", null, "Todo list"),
        FunctionData("Todo list", null, "Todo list")
    )
    override val functions: Flow<List<FunctionData>> = flow {
        emit(example)
    }
}