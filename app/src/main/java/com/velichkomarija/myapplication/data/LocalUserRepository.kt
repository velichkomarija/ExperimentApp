package com.velichkomarija.myapplication.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LocalUserRepository : UserDataRepository {
    override val userData: Flow<UserData> = flow {
        emit(UserData())
    }
}