package com.example.data.viewmodel

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.data.local.entities.NavigationItem
import com.example.navigation.Routes
import com.google.firebase.auth.FirebaseAuth

class HomeViewModel(): ViewModel() {
    private val TAG = HomeViewModel::class.simpleName

    val isUserLoggedIn : MutableLiveData<Boolean> = MutableLiveData()

    val emailId: MutableLiveData<String> = MutableLiveData()

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
                isUserLoggedIn.value = false
                navController.navigate(Routes.Login.route)
                Log.d(TAG, "Inside sign out success state...")
            } else {
                Log.d(TAG, "Logged-In User: ${it.currentUser!!.displayName}")
                Log.d(TAG, "Inside sign out is not complete state...wait")
            }
        }
        firebaseAuth.addAuthStateListener(authStateListener)
    }

    fun checkForActiveSession(){
        if (FirebaseAuth.getInstance().currentUser != null){
            Log.d(TAG, "Valid Session")
            isUserLoggedIn.value = true
        }else{
            Log.d(TAG, "User is not logged in")
            Log.d(TAG, "User Login-State: ${FirebaseAuth.getInstance().currentUser != null}")
            isUserLoggedIn.value = false
        }
    }

    fun getUserData(){
        FirebaseAuth.getInstance().currentUser?.also { //returns user if it is not null
//            Log.d(TAG, "User's name: ${it.displayName}")
            it.email?.also {
                email ->
                    emailId.value = email
            }
        }
    }
}