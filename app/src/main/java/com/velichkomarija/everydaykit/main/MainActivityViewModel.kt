package com.velichkomarija.everydaykit.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.velichkomarija.everydaykit.data.functions.FunctionData
import com.velichkomarija.everydaykit.data.functions.FunctionsDataRepository
import com.velichkomarija.everydaykit.data.sync.AuthRepository
import com.velichkomarija.everydaykit.data.user.UserData
import com.velichkomarija.everydaykit.data.user.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    userDataRepository: UserDataRepository,
    functionsDataRepository: FunctionsDataRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _syncUiState = MutableStateFlow(SyncUiState(isLinked = false))
    val syncUiState =_syncUiState.asStateFlow()

    private val _effects = Channel<SyncEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    val uiState: StateFlow<MainActivityUiState> = combine(
        userDataRepository.userData, //todo не грузить, если не дебаг
        functionsDataRepository.functions,
    ) { userData, functionData ->
        MainActivityUiState.Success(userData, functionData)
    }
        .stateIn(
            scope = viewModelScope,
            initialValue = MainActivityUiState.Loading,
            started = SharingStarted.WhileSubscribed(1_000),
        )

    init {
       viewModelScope.launch {
           authRepository.state.collect{

           }
       }
    }

    fun onSignInClick() {
        if (_syncUiState.value.isBusy) return
        _syncUiState.update { it.copy(isBusy = true, error = null) }
        _effects.trySend(SyncEffect.StartGoogleSignIn)
    }

    fun onSignInResult(result: Result<GoogleIdTokenCredential>) {
        viewModelScope.launch {
            result.onSuccess { cred ->
                authRepository.setLinked(cred.id)
            }.onFailure { e ->
                // todo здесь понятное объянение
                _syncUiState.update { it.copy(error = e.message ?: e.javaClass.simpleName) }
            }
            _syncUiState.update { it.copy(isBusy = false) }
        }
    }

    // todo при необходимости загрузки по емейлу
    fun onSyncClick() {
        if (_syncUiState.value.isBusy) return
        viewModelScope.launch {
            _syncUiState.update { it.copy(isBusy = true, error = null) }
            try {
                // ... твой реальный синк
                authRepository.markSynced()
            } catch (t: Throwable) {
                // todo здесь понятное объянение
                _syncUiState.update { it.copy(error = t.message ?: "Sync failed") }
            } finally {
                _syncUiState.update { it.copy(isBusy = false) }
            }
        }
    }

    fun unlink() { viewModelScope.launch { authRepository.setUnlinked() } }

}

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState
    data class Success(
        val userData: UserData,
        val functions: List<FunctionData>
    ) : MainActivityUiState
}

sealed interface SyncEffect {
    data object StartGoogleSignIn : SyncEffect
}

data class SyncUiState(
    val isLinked: Boolean,
    val email: String? = null,
    val isBusy: Boolean = false,
    val lastSync: Long? = null,
    val error: String? = null
)