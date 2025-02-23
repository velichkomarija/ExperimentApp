@file:OptIn(ExperimentalMaterialApi::class)

package com.velichkomarija.everydaykit.todo.addnew

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.velichkomarija.everydaykit.R
import com.velichkomarija.everydaykit.ui.theme.Dimens
import com.velichkomarija.everydaykit.ui.theme.Typography
import com.velichkomarija.everydaykit.uicomponents.AddOrUpdateTopAppBar
import com.velichkomarija.everydaykit.uicomponents.TaskDetailTopAppBar

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddOrUpdateScreen(
    @StringRes topBarTitle: Int?,
    onUpdate: () -> Unit,
    onBack: () -> Unit,
    onDelete: () -> Unit,
    isNew: Boolean = true,
    modifier: Modifier = Modifier,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    viewModel: AddOrUpdateTaskViewModel = hiltViewModel(),
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = {
            if (isNew) {
                AddOrUpdateTopAppBar(topBarTitle, onBack)
            } else {
                TaskDetailTopAppBar(onBack, viewModel::deleteTask)
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = viewModel::saveTask) {
                Icon(Icons.Filled.Done, stringResource(id = R.string.save_task))
            }
        }
    ) { paddingValues ->
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        AddOrUpdateTaskContent(
            loading = uiState.isLoading,
            title = uiState.title,
            description = uiState.description,
            onTitleChanged = viewModel::updateTitle,
            onDescriptionChanged = viewModel::updateDescription,
            modifier = Modifier.padding(paddingValues)
        )

        LaunchedEffect(uiState.isSaved) {
            if (uiState.isSaved) {
                onUpdate()
            }
        }

        LaunchedEffect(uiState.isDeleted) {
            if (uiState.isDeleted) {
                onDelete()
            }
        }

        uiState.userMessage?.let { userMessage ->
            val snackbarText = stringResource(userMessage)
            LaunchedEffect(scaffoldState, viewModel, userMessage, snackbarText) {
                scaffoldState.snackbarHostState.showSnackbar(snackbarText)
                viewModel.snackbarMessageShown()
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun AddOrUpdateTaskContent(
    loading: Boolean,
    title: String,
    description: String,
    onTitleChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (loading) {
        PullRefreshIndicator(
            refreshing = true,
            state = rememberPullRefreshState(
                refreshing = true,
                onRefresh = { }),
        )
    } else {
        Column(
            modifier
                .fillMaxSize()
                .padding(all = Dimens.LargePadding)
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = onTitleChanged,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.title_hint),
                        style = Typography.headlineLarge
                    )
                },
                textStyle = Typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                maxLines = 2
            )

            OutlinedTextField(
                value = description,
                onValueChange = onDescriptionChanged,
                placeholder = { androidx.compose.material.Text(stringResource(id = R.string.description_hint)) },
                modifier = Modifier
                    .height(360.dp)
                    .fillMaxWidth(),
            )
        }
    }
}