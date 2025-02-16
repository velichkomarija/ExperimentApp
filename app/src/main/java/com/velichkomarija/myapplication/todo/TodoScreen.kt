package com.velichkomarija.myapplication.todo

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.velichkomarija.myapplication.R
import com.velichkomarija.myapplication.data.todo.Task

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TodoScreen(
    onTaskClick: (Task) -> Unit,
    onAddNewTask: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TodoViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    Scaffold(scaffoldState = scaffoldState,
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = onAddNewTask) {
                Icon(Icons.Filled.Add, stringResource(id = R.string.add_task))
            }
        }
    ) { paddingValues ->
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        TasksContent(
            loading = uiState.isLoading,
            tasks = uiState.items,
            onTaskClick = onTaskClick,
            onTaskCheckedChange = viewModel::completeTask,
            modifier = Modifier.padding(paddingValues)
        )
    }
}