package com.example.data.repository

import com.example.util.Resource
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    val firebaseAuth: FirebaseAuth): AuthenticationRepository {

        override fun googleSignIn(credential: AuthCredential): Flow<Resource<AuthResult>>{
            return flow {
                emit(Resource.Loading())
                val result = firebaseAuth.signInWithCredential(credential).await()
                emit(Resource.Success(result))
            }.catch {
                emit(Resource.Error(it.message.toString()))
            }
        }
}