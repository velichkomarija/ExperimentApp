package com.velichkomarija.myapplication.data

import kotlinx.coroutines.flow.Flow


interface FunctionsDataRepository {
    val functions: Flow<List<FunctionData>>
}