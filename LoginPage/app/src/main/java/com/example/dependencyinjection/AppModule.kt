package com.example.dependencyinjection

import com.example.data.repository.AuthenticationRepository
import com.example.data.repository.AuthenticationRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//class AppModule {
//    @Provides
//    @Singleton
//    fun provideFirebaseAuth() = FirebaseAuth.getInstance()
//
//    @Provides
//    @Singleton
//    fun provideRepositoryImpl(firebaseAuth: FirebaseAuth): AuthenticationRepository{
//        return AuthenticationRepositoryImpl(firebaseAuth)
//    }
//}