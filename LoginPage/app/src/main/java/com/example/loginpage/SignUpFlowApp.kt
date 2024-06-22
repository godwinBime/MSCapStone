package com.example.loginpage

import android.app.Application
import com.google.firebase.FirebaseApp

class SignUpFlowApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}