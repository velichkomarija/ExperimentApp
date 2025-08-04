package com.velichkomarija.everydaykit

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

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