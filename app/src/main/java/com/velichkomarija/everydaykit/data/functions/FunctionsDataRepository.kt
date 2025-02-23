package com.velichkomarija.everydaykit.data.functions

import kotlinx.coroutines.flow.Flow


interface FunctionsDataRepository {
    val functions: Flow<List<FunctionData>>
}