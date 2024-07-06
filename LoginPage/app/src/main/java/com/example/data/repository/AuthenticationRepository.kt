package com.example.data.repository

import com.example.util.Resource
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {
    fun googleSignIn(credential: AuthCredential): Flow<Resource<AuthResult>>
}