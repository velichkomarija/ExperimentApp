package com.velichkomarija.myapplication.data.user

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LocalUserRepository : UserDataRepository {
    override val userData: Flow<UserData> = flow {
        emit(UserData())
    }
}