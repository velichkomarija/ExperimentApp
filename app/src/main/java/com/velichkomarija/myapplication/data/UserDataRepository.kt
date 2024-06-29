package com.velichkomarija.myapplication.data

import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    val userData: Flow<UserData>
}