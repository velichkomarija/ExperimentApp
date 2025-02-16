package com.velichkomarija.myapplication.data.user

import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    val userData: Flow<UserData>
}