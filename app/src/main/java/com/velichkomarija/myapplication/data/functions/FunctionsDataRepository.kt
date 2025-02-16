package com.velichkomarija.myapplication.data.functions

import kotlinx.coroutines.flow.Flow


interface FunctionsDataRepository {
    val functions: Flow<List<FunctionData>>
}