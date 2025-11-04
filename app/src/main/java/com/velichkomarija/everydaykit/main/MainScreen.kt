package com.velichkomarija.everydaykit.main

import androidx.activity.ComponentActivity
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.velichkomarija.everydaykit.BuildUtils
import com.velichkomarija.everydaykit.NavigateActions
import com.velichkomarija.everydaykit.main.MainActivityUiState.Success
import com.velichkomarija.everydaykit.signInWithGoogle
import com.velichkomarija.everydaykit.uicomponents.ItemButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun MainScreen(
    viewModel: MainActivityViewModel = hiltViewModel(),
    navigateActions: NavigateActions
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val ui by viewModel.syncUiState.collectAsStateWithLifecycle()
    val activity = LocalContext.current as ComponentActivity

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

                LaunchedEffect(Unit) {
                    viewModel.effects.collect { eff ->
                        when (eff) {
                            SyncEffect.StartGoogleSignIn -> {
                                //todo проверить
                                val result = withContext(Dispatchers.Main) {
                                    activity.signInWithGoogle()
                                }
                                viewModel.onSignInResult(result)
                            }
                        }
                    }
                }

                SyncStatusCard(
                    isLinked = ui.isLinked,
                    email = ui.email,
                    isBusy = ui.isBusy,
                    lastSync = ui.lastSync,
                    error = ui.error,
                    onSignIn = viewModel::onSignInClick,
                    onSync = viewModel::onSyncClick,
                    onUnlink = viewModel::unlink
                )


                LazyColumn {
                    (uiState as Success).functions.forEach { functionData ->
                        item {
                            ItemButton(
                                title = functionData.name,
                                description = functionData.description,
                                onClick = { navigateActions.navigate(functionData.navigationTag) },
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }
        }
    }
}