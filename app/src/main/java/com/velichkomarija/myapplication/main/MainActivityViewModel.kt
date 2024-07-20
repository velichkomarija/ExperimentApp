package com.velichkomarija.myapplication.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.velichkomarija.myapplication.data.FunctionData
import com.velichkomarija.myapplication.data.FunctionsDataRepository
import com.velichkomarija.myapplication.data.UserData
import com.velichkomarija.myapplication.data.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    userDataRepository: UserDataRepository,
    functionsDataRepository: FunctionsDataRepository
) :
    ViewModel() {
    val uiState: StateFlow<MainActivityUiState> = combine(
        userDataRepository.userData,
        functionsDataRepository.functions
    ) { userData, functionData ->
        MainActivityUiState.Success(userData, functionData)
    }
        .stateIn(
            scope = viewModelScope,
            initialValue = MainActivityUiState.Loading,
            started = SharingStarted.WhileSubscribed(5_000),
        )
}

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState
    data class Success(
        val userData: UserData,
        val functions: List<FunctionData>
    ) : MainActivityUiState
}