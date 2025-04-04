package com.velichkomarija.everydaykit.todo

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.velichkomarija.everydaykit.R
import com.velichkomarija.everydaykit.data.todo.Task
import com.velichkomarija.everydaykit.uicomponents.TodoTopAppBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TodoScreen(
    @StringRes userMessage: Int,
    onTaskClick: (Task) -> Unit,
    onAddNewTask: () -> Unit,
    onUserMessageDisplayed: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TodoViewModel = hiltViewModel(),
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddNewTask) {
                Icon(Icons.Filled.Add, stringResource(id = R.string.add_task))
            }
        },
        topBar = { TodoTopAppBar(onBack) }
    ) { paddingValues ->
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        TasksContent(
            tasks = uiState.items,
            onTaskClick = onTaskClick,
            onTaskCheckedChange = viewModel::completeTask,
            modifier = Modifier.padding(paddingValues)
        )

        uiState.userMessage?.let { message ->
            val snackbarText = stringResource(message)
            LaunchedEffect(snackbarHostState, viewModel, message, snackbarText) {
                snackbarHostState.showSnackbar(snackbarText)
                viewModel.snackbarMessageShown()
            }
        }

        val currentOnUserMessageDisplayed by rememberUpdatedState(onUserMessageDisplayed)
        LaunchedEffect(userMessage) {
            if (userMessage != 0) {
                viewModel.showEditResultMessage(userMessage)
                currentOnUserMessageDisplayed()
            }
        }
    }
}