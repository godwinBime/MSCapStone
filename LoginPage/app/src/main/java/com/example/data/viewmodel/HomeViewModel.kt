package com.example.data.viewmodel

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.data.local.entities.NavigationItem
import com.example.navigation.Routes
import com.google.firebase.auth.FirebaseAuth

class HomeViewModel(): ViewModel() {
    private val TAG = HomeViewModel::class.simpleName
//    private val auth = FirebaseAuth.getInstance()

    val isUserLoggedIn : MutableLiveData<Boolean> = MutableLiveData()
    var logOutInProgress = mutableStateOf(false)
    var checkActiveSessionInProgress = mutableStateOf(false)
//    val isSessionOver : MutableLiveData<Boolean> = MutableLiveData()

//    val emailId: MutableLiveData<String> = MutableLiveData()
//    val fullNames: MutableLiveData<String> = MutableLiveData()

    val navigationItemList = listOf<NavigationItem>(
        NavigationItem(
            title = "Home",
            description = "Home Screen",
            itemId = "homeScreen",
            icon = Icons.Default.Home),

        NavigationItem(
            title = "Profile",
            description = "Profile Screen",
            itemId = "profileScreen",
            icon = Icons.Default.Person),

        NavigationItem(
            title = "Change Password",
            description = "Change Password Screen",
            itemId = "changePassword",
            icon = Icons.Default.Password),

        NavigationItem(
            title = "Setting",
            description = "Setting Screen",
            itemId = "settingScreen",
            icon = Icons.Default.Settings),

        NavigationItem(
            title = "Logout",
            description = "Logout Screen",
            itemId = "logoutScreen",
            icon = Icons.AutoMirrored.Filled.Logout
        )
    )

    fun logOut(navController: NavHostController){
        logOutInProgress.value = true
        val firebaseAuth = FirebaseAuth.getInstance()
        if (firebaseAuth.currentUser != null) {
            firebaseAuth.signOut()
            val authStateListener = FirebaseAuth.AuthStateListener {
                if (it.currentUser == null) {
                    isUserLoggedIn.value = false
                    logOutInProgress.value = false
                    navController.navigate(Routes.Login.route)
                    Log.d(TAG, "Inside sign out success state...")
                } else {
                    Log.d(TAG, "Logged-In User: ${it.currentUser!!.displayName}")
                    Log.d(TAG, "Inside sign out is not complete state...wait")
                }
            }
            firebaseAuth.addAuthStateListener(authStateListener)
        }else{
            Log.d(TAG, "Error:-> No user is logged in...Login to continue")
            logOutInProgress.value = false
            navController.navigate(Routes.Login.route)
        }
    }

    fun checkForActiveSession(){
        checkActiveSessionInProgress.value = true
        if (FirebaseAuth.getInstance().currentUser != null){
            Log.d(TAG, "Valid Session")
            isUserLoggedIn.value = true
            checkActiveSessionInProgress.value = false
        }else{
            Log.d(TAG, "User is not logged in")
            Log.d(TAG, "User Login-State: ${FirebaseAuth.getInstance().currentUser != null}")
            isUserLoggedIn.value = false
            checkActiveSessionInProgress.value = true
        }
    }
/*
    fun getUserData(signUpPageViewModel: SignUpPageViewModel){
        val user = FirebaseAuth.getInstance().currentUser
        val providerId = signUpPageViewModel.checkUserProvider(user)
        user?.also {
            when(providerId){
                "google.com" -> {
                    Log.d(TAG, "User's name: ${it.displayName}")
//            it.email?.also {
//                email ->
//                    emailId.value = email
//            }
//                    it.displayName?.also {
//                            name ->
//                        fullNames.value = name.substringBefore(" ")
//                    }
//                }
//                "password" -> {
//                    val userId = auth.currentUser?.uid
//                    val userId = user.uid
//                    signUpPageViewModel.fetchUserData(signUpPageViewModel = signUpPageViewModel, userId = userId){user ->
//                        user.firstName.also {
//                            fullNames.value = user.firstName //+ " " + user.lastName
//                            if (fullNames.value!!.isEmpty()){
//                                Log.d(TAG, "User's name is empty")
//                                fullNames.value = user.firstName
//                            }else{
//                                Log.d(TAG, "User's name is not empty, name: ${fullNames.value}")
//                            }
                        }
                    }
                }
            }
        }
//        FirebaseAuth.getInstance().currentUser?.also { //returns user if it is not null
//            Log.d(TAG, "User's name: ${it.displayName}")
////            it.email?.also {
////                email ->
////                    emailId.value = email
////            }
//            it.displayName?.also {
//                name ->
//                fullNames.value = name
//            }
//        }
    }

 */
}