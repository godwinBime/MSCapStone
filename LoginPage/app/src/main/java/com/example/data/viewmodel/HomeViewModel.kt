package com.example.data.viewmodel

import android.content.Context
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
import com.example.data.local.entities.Constant.SERVERCLIENT
import com.example.data.local.entities.NavigationItem
import com.example.navigation.Routes
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class HomeViewModel: ViewModel() {
    private val TAG = HomeViewModel::class.simpleName
    val isUserLoggedIn : MutableLiveData<Boolean> = MutableLiveData()
    var checkActiveSessionInProgress = mutableStateOf(false)
    val sessionJustResumed : MutableLiveData<Boolean> = MutableLiveData(false)
    val sessionJustStarted : MutableLiveData<Boolean> = MutableLiveData(false)

//    init {
//
//        homeSessionJustStarted.value = true
//    }

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

    fun resetStartState(){
        sessionJustStarted.value = false
    }

    fun resetResumeState(){
        sessionJustResumed.value = false
    }

    fun googleSignInOptions(): GoogleSignInOptions {
        val gso = GoogleSignInOptions.Builder()
            .requestEmail()
            .requestIdToken(SERVERCLIENT)
            .requestProfile()
            .build()
        return gso
    }

    /**
     * Signs out the current signed-in user if any.
     * It also clears the account previously selected by
     * the user and a future sign in attempt will require
     * the user pick an account again.
     */
    private fun googleLogout(context: Context, navController: NavHostController){
        val gso = googleSignInOptions()
        val googleSignInClient = GoogleSignIn.getClient(context, gso)
        googleSignInClient.signOut()
            .addOnCompleteListener{signOutTask ->
                if(signOutTask.isSuccessful){
                    Log.d(TAG, "Successfully signed out from Google.")
                    navController.navigate(Routes.Login.route)
                }else{
                    Log.d(TAG, "Sign out failed: ${signOutTask.exception?.message}")
                }
            }
            .addOnFailureListener{
                Log.d(TAG, "SignOut addOnFailureListener Sign out failed:")
            }
    }

    fun logOut(navController: NavHostController,
               signUpPageViewModel: SignUpPageViewModel, context: Context){
        checkActiveSessionInProgress.value = true
        val providerId = signUpPageViewModel.checkUserProvider(user = FirebaseAuth.getInstance().currentUser)
        if (providerId == "google.com"){
            signOut(navController = navController)
            Log.d(TAG, "Inside sign out google account....")
            googleLogout(context = context, navController = navController)
            checkActiveSessionInProgress.value = false
        }else if (providerId == "password"){
            signOut(navController = navController)
            checkActiveSessionInProgress.value = false
        }else{
            if (isUserLoggedIn.value == null || isUserLoggedIn.value == false) {
                Log.d(TAG, "Invalid logout call...")
                navController.navigate(Routes.Login.route)
            }
            checkActiveSessionInProgress.value = false
        }
    }

    private fun signOut(navController: NavHostController){
        val firebaseAuth = FirebaseAuth.getInstance()
        if (firebaseAuth.currentUser != null) {
            firebaseAuth.signOut()
            val authStateListener = FirebaseAuth.AuthStateListener {
                if (it.currentUser == null) {
                    isUserLoggedIn.value = false
                    navController.navigate(Routes.Login.route)
//                    navController.popBackStack()
                    Log.d(TAG, "Inside sign out success state...")
                } else {
                    Log.d(TAG, "Logged-In User: ${it.currentUser!!.displayName}")
                    Log.d(TAG, "Inside sign out is not complete state...wait")
                }
            }
            firebaseAuth.addAuthStateListener(authStateListener)
        }else{
            Log.d(TAG, "Error:-> No user is logged in...Login to continue")
            navController.navigate(Routes.Login.route)
        }
    }

    fun checkForActiveSession(){
        checkActiveSessionInProgress.value = true
        if (FirebaseAuth.getInstance().currentUser != null){
            Log.d(TAG, "Valid Session")
            isUserLoggedIn.value = true
            sessionJustResumed.value = true
            checkActiveSessionInProgress.value = false
        }else{
            sessionJustStarted.value = true
            Log.d(TAG, "User is not logged in")
            Log.d(TAG, "User Login-State: ${FirebaseAuth.getInstance().currentUser != null}")
            isUserLoggedIn.value = false
            checkActiveSessionInProgress.value = false
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