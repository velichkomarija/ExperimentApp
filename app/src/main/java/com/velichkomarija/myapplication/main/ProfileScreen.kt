package com.velichkomarija.myapplication.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.velichkomarija.myapplication.main.MainActivityUiState.Success
import com.velichkomarija.myapplication.uicomponents.ItemButton

@Composable
fun ProfileScreen(
    viewModel: MainActivityViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        if (uiState is Success) {
            Greeting(
                userData = (uiState as Success).userData,
            )
            LazyColumn {
                (uiState as Success).functions.forEach { functionData ->
                    item {
                        ItemButton(
                            title = functionData.name,
                            description = functionData.description,
                            onClick = { },
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}