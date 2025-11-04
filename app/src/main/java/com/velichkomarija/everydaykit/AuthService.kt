package com.velichkomarija.everydaykit

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthService {
    private val tag = this.javaClass.name
    private val authService: FirebaseAuth = Firebase.auth
    private var user = authService.currentUser

    fun checkUser() {
        if (user == null) {
            singInAnonymously()
        }
    }

    fun getUserUid(): String {
        return user?.uid ?: UNKNOWN_USER
    }

    private fun singInAnonymously() {
        authService.signInAnonymously()
            .addOnSuccessListener { result ->
                val user = result.user
                Log.d(tag, "Success: UID = ${user?.uid}")
            }
            .addOnFailureListener { e ->
                Log.e(tag, "Error with ", e)
            }
    }

    companion object {
        const val UNKNOWN_USER = "unknown"
    }
}

suspend fun ComponentActivity.signInWithGoogle(): Result<GoogleIdTokenCredential> {
    val serverClientId = getString(R.string.default_web_client_id)

    // 1) Готовим опцию для Google ID токена
    val googleIdOption = GetGoogleIdOption.Builder()
        .setServerClientId(serverClientId)
        .setFilterByAuthorizedAccounts(false) // показать все аккаунты; true — только уже авторизованные
        .setAutoSelectEnabled(true)           // если один вариант — автоселект
        .setNonce(/* сгенерируй криптостойкую nonce для защиты от replay */ null)
        .build()

    // 2) Формируем общий запрос для Credential Manager
    val request = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()

    val credentialManager = CredentialManager.create(this)

    return try {
        // 3) Показываем системный bottom sheet и получаем credential
        val result = withContext(Dispatchers.Main) {
            credentialManager.getCredential(request = request, context = this@signInWithGoogle)
        }

        // 4) Достаём GoogleIdTokenCredential
        val credential = result.credential
        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)

        // Полезные поля (для UX), но главный артефакт — idToken:
        val idToken = googleIdTokenCredential.idToken  // <-- JWT
        val email = googleIdTokenCredential.id         // e-mail аккаунта
        // displayName, givenName, familyName, profilePictureUri и т.д. тоже доступны

        Result.success(googleIdTokenCredential)
    } catch (e: GetCredentialCancellationException) {
        Result.failure(e) // пользователь отменил
    } catch (e: GetCredentialException) {
        Result.failure(e) // общая ошибка Credential Manager
    }
}