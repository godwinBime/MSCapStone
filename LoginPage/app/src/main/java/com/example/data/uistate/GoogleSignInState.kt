package com.example.data.uistate

import com.google.firebase.auth.AuthResult

data class GoogleSignInState(
    val success: AuthResult? = null,
    val loading: Boolean = false,
    val error: String = ""
)

data class GoogleUserData(
    val fullNames: String = "",
    val profilePicture: String = ""
)