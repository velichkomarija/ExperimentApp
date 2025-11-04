package com.velichkomarija.everydaykit.data.sync

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.velichkomarija.everydaykit.main.SyncUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object AuthPrefs {
    val IS_LINKED = booleanPreferencesKey("is_linked")
    val EMAIL = stringPreferencesKey("email")
    val LAST_SYNC = longPreferencesKey("last_sync")
}

class AuthRepository(private val dataStore: DataStore<Preferences>) {
    val state: Flow<SyncUiState> = dataStore.data.map { pref ->
        SyncUiState(
            isLinked = pref[AuthPrefs.IS_LINKED] ?: false,
            email = pref[AuthPrefs.EMAIL],
            lastSync = pref[AuthPrefs.LAST_SYNC]
        )
    }

    suspend fun setLinked(email: String) {
        dataStore.edit {
            it[AuthPrefs.IS_LINKED] = true
            it[AuthPrefs.EMAIL] = email
        }
    }

    suspend fun setUnlinked() {
        dataStore.edit {
            it[AuthPrefs.IS_LINKED] = false
            it[AuthPrefs.EMAIL] = ""
        }
    }

    suspend fun markSynced(nowMillis: Long = System.currentTimeMillis()) {
        dataStore.edit { it[AuthPrefs.LAST_SYNC] = nowMillis }
    }
}
