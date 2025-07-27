package com.velichkomarija.everydaykit.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.velichkomarija.everydaykit.BuildUtils
import com.velichkomarija.everydaykit.main.MainActivityUiState.Success
import com.velichkomarija.everydaykit.uicomponents.ItemButton

@Composable
fun MainScreen(
    viewModel: MainActivityViewModel = hiltViewModel(),
    openTodoList: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentWindowInsets = WindowInsets.systemBars
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (uiState is Success) {
                if (BuildUtils.isDebug()) {
                    Greeting(
                        userData = (uiState as Success).userData,
                    )
                }
                LazyColumn {
                    (uiState as Success).functions.forEach { functionData ->
                        item {
                            ItemButton(
                                title = functionData.name,
                                description = functionData.description,
                                onClick = { openTodoList.invoke() },
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }
        }
    }
}