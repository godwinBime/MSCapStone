package com.example.data.home

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.data.NavigationItem
import com.example.navigation.Routes
import com.google.firebase.auth.FirebaseAuth

class HomeViewModel: ViewModel() {
    private val TAG = HomeViewModel::class.simpleName

    val navigationItemList = listOf<NavigationItem>(
        NavigationItem(title = "Home",
            description = "Home Screen",
            itemId = "homeScreen",
            icon = Icons.Default.Home),

        NavigationItem(title = "Profile",
            description = "Profile Screen",
            itemId = "profileScreen",
            icon = Icons.Default.Person),

        NavigationItem(title = "Setting",
            description = "Setting Screen",
            itemId = "settingScreen",
            icon = Icons.Default.Settings),

        NavigationItem(title = "Logout",
            description = "Logout Screen",
            itemId = "logoutScreen",
            icon = Icons.AutoMirrored.Filled.Logout
        )
    )

    fun logOut(navController: NavHostController){
        val firebaseAuth = FirebaseAuth
            .getInstance()
        firebaseAuth.signOut()

        val authStateListener = FirebaseAuth.AuthStateListener {
            if (it.currentUser == null) {
                navController.navigate(Routes.Login.route)
                Log.d(TAG, "Inside sign out success state")
            } else {
                Log.d(TAG, "Inside sign out is not complete state...")
            }
        }
        firebaseAuth.addAuthStateListener(authStateListener)
    }
}