package com.velichkomarija.everydaykit.data.user

import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    val userData: Flow<UserData>
}