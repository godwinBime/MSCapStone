package com.example.data.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.data.local.entities.Constant.SERVERCLIENT
import com.example.navigation.Routes
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class UpdateProfileViewModel: ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val TAG = UpdateProfileViewModel::class.simpleName
    var displayUserProfileInProgress = mutableStateOf(false)

    var oldPassword by mutableStateOf("")
    var newPassword by mutableStateOf("")
    var updatedFirstName by mutableStateOf("")
    var updatedLastName by mutableStateOf("")
    var updatedPhoneNumber by mutableStateOf("")
    var updatedEmail by mutableStateOf("")

    fun updateUserProfile(navController: NavHostController) {
        Log.d(TAG, "To be updated First name: $updatedFirstName")
        Log.d(TAG, "To be updated Last name: $updatedLastName")
        Log.d(TAG, "To be updated Phone number: $updatedPhoneNumber")
        displayUserProfileInProgress.value = true
        viewModelScope.launch {
            updateProfile(
                firstName = updatedFirstName,
                lastName = updatedLastName,
                phoneNumber = updatedPhoneNumber,
                navController = navController
            )
        }
    }

    private suspend fun updateProfile(firstName: String, lastName: String,
                              phoneNumber: String, email: String = "",
                              navController: NavHostController){
        if (auth.currentUser != null){
            Log.d(TAG, "Inside update user data call and user is found")
            val userId = auth.currentUser?.uid
            val updateUserData = hashMapOf<String, Any>(
                "firstName" to firstName,
                "lastName" to lastName,
                "phoneNumber" to phoneNumber
//            "email" to email
            )
            viewModelScope.launch {
                try {
                    if (userId != null) {
                        Log.d(TAG, "Inside update user data call and userId ($userId) is found")
                        val query = firestore.collection("userdata").whereEqualTo("userId", userId)
                        query.get()
                            .addOnSuccessListener { documentQuerySnapShot ->
                                if (!documentQuerySnapShot.isEmpty){
                                    val documentSnapshot = documentQuerySnapShot.documents[0]
                                    Log.d(TAG, "DocumentId inside updateProfile(): ${documentSnapshot.id}")
                                    viewModelScope.launch {
                                        val documentRef = firestore.collection("userdata")
                                            .document(documentSnapshot.id)
                                        documentRef.update(updateUserData)
                                            .addOnSuccessListener {
                                                Log.d(TAG, "Success in Updating user data...")
                                                navController.navigate(Routes.UserProfile.route)
                                            }
                                            .addOnFailureListener{
                                                Log.d(TAG, "Failed to Update user data...")
                                            }
                                    }
                                }
                            }
                            .addOnFailureListener{
                                Log.d(TAG, "Failed to find documentID...")
                            }
                    }else{
                        Log.d(TAG, "Error from updateProfile(): No userID detected...")
                    }
                }catch (e: Exception){
                    Log.d(TAG, "updateProfile Exception: ${e.message}")
                }finally {
                    displayUserProfileInProgress.value = false
                }
            }
        }else{
            Log.d(TAG, "No active user found when updateProfile() was called..")
        }
    }

    fun changeUserPassword(navController: NavHostController){
        changePassword(
            navController = navController
        ){ _, _ ->
            displayUserProfileInProgress.value = true
        }
    }

    private fun changePassword(navController: NavHostController,
                               callback: (Boolean, String) -> Unit){
        val user = auth.currentUser
        Log.d(TAG, "Credentials received:\nEmail: ${user?.email} \nOld Password: $oldPassword New password: $newPassword")
        try {
            user?.let {
                //  Re-authenticate user before changing password
                val credential = EmailAuthProvider.getCredential(user.email!!, oldPassword)
                it.reauthenticate(credential)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // if re-authentication is successful, update password
                            user.updatePassword(newPassword)
                                .addOnCompleteListener { updateTask ->
                                    if (updateTask.isSuccessful) {
                                        Log.d(TAG, "Password change successful")
                                        callback(true, "Password change successful")
                                        navController.navigate(Routes.UserProfile.route)
                                    } else {
                                        Log.d(TAG, "Password change failed")
                                        callback(false, "Password change failed")
                                    }
                                }
                        } else {
                            Log.d(TAG, "re-authentication failed...")
                            callback(false, "re-authentication failed...")
                        }
                    }
            } ?: run {
                Log.d(TAG, "No active user found when changePassword() was called..")
                callback(false, "No user is logged in")
            }
        }catch (e: Exception){
            Log.d(TAG, "changePassword() Exception was triggered.. ${e.message}")
        }finally {
            displayUserProfileInProgress.value = false
        }
    }

    fun deleteCurrentProfile(navController: NavHostController,
                             signUpPageViewModel: SignUpPageViewModel,
                             providerId: String){
//        val user = auth.currentUser
//        val providerId = signUpPageViewModel.checkUserProvider(user = user)
        displayUserProfileInProgress.value = true
        if (providerId == "password") {
            deleteProfile(navController = navController, signUpPageViewModel = signUpPageViewModel,
                userType = providerId)
        }
    }

    private fun deleteUsernamePassword(navController: NavHostController,
                                       signUpPageViewModel: SignUpPageViewModel,
                                       userType: String){
        val user = auth.currentUser
//        val userType = signUpPageViewModel.checkUserProvider(user = user)
        try{
            if (user != null){
                Log.d(TAG, "About to delete logged-in user...")
                user.delete()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "Email and password deleted successfully...")
                            if (userType == "password") {
                                navController.navigate(Routes.Login.route)
                            } else if (userType == "google.com") {
                                Log.d(TAG, "Google account user deleted, no data to delete")
                                navController.navigate(Routes.Login.route)
                            } else {
                                Log.d(TAG, "Error: Undefined user...")
                            }
                        }
                    }
                    .addOnFailureListener{
                        Log.d(TAG, "Error: addOnFailureListener:-> Email and password not deleted...")
                    }
            }else{
                Log.d(TAG, "Error: No logged-in user found when deleteUsernamePassword() was called...")
            }
        }catch (e: Exception){
            Log.d(TAG, "deleteUsernamePassword Exception: ${e.message}")
        }finally {
            displayUserProfileInProgress.value = false
        }
    }

    private fun deleteProfile(navController: NavHostController,
                              signUpPageViewModel: SignUpPageViewModel,
                              userType: String){
        if (auth.currentUser != null) {
            displayUserProfileInProgress.value = true
            val userId = auth.currentUser?.uid
            viewModelScope.launch {
                try {
                    val deleteQuery = firestore.collection("userdata").whereEqualTo("userId", userId)
                    deleteQuery.get()
                        .addOnSuccessListener { deleteDocumentQuerySnapshot ->
                            if (!deleteDocumentQuerySnapshot.isEmpty){
                                val deleteDocumentSnapshot = deleteDocumentQuerySnapshot.documents[0]
                                Log.d(TAG, "DocumentId inside deleteProfile(): ${deleteDocumentSnapshot.id}")
                                viewModelScope.launch {
                                    firestore.collection("userdata").document(deleteDocumentSnapshot.id).delete()
                                        .addOnCompleteListener{task ->
                                            if (task.isSuccessful){
                                                Log.d(TAG, "Data of User with ID ( ${deleteDocumentSnapshot.id}) successfully deleted")
                                                deleteUsernamePassword(
                                                    navController = navController,
                                                    signUpPageViewModel = signUpPageViewModel,
                                                    userType = userType
                                                )
                                            }else{
                                                Log.d(TAG, "Call from deleteProfile(): task to delete user profile failed")
                                            }
                                        }
//                                        .addOnSuccessListener {
//                                            Log.d(TAG, "Data of User with ID ( ${deleteDocumentSnapshot.id}) successfully deleted")
//                                            deleteUsernamePassword(
//                                                navController = navController,
//                                                signUpPageViewModel = signUpPageViewModel,
//                                                userType = userType
//                                            )
//                                        }
                                        .addOnFailureListener{
                                            Log.d(TAG, "addOnFailureListener Call from deleteProfile(): task to delete user profile failed")
                                        }
                                }
                            }else{
//                                deleteUsernamePassword(
//                                    navController = navController,
//                                    signUpPageViewModel = signUpPageViewModel,
//                                    userType = userType
//                                )
                                Log.d(TAG, "Error: DocumentId Not found")
                            }
                        }
                }catch (e: Exception){
                    Log.d(TAG, "deleteUser Exception: ${e.message}")
                }finally {
                    displayUserProfileInProgress.value = false
                }
            }
        }else{
            Log.d(TAG, "No active user found when deleteProfile() was called..")
        }
    }

    fun deleteGoogleCredentials(navController: NavHostController,
                                signUpPageViewModel: SignUpPageViewModel,
                                context: Context, homeViewModel: HomeViewModel,
                                providerId: String){
        val user = auth.currentUser
//        val providerId = signUpPageViewModel.checkUserProvider(user = user)
        if (providerId == "google.com"){
            viewModelScope.launch {
                deleteGoogleUser(navController = navController, providerId = providerId,
                    context = context, homeViewModel = homeViewModel)
            }
        }
    }

    private suspend fun deleteGoogleUser(navController: NavHostController, providerId: String,
                                         context: Context, homeViewModel: HomeViewModel){
        val user = auth.currentUser
        if (user != null && user.providerData.any{it.providerId == "google.com"}){
            val account = GoogleSignIn.getLastSignedInAccount(context)
            val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
            try {
                // Reauthenticate user
                user.reauthenticate(credential).await()
                user.delete()
                    .addOnCompleteListener{task ->
                        if (task.isSuccessful){
                            homeViewModel.checkForActiveSession()
                            homeViewModel.logOut(navController = navController)
                            navController.navigate(Routes.Login.route)
                            if (auth.currentUser == null) {
                                Log.d(TAG, "Google account successfully deleted...")
                            }
                            Log.d(TAG, "Any active user after account deletion?: ${auth.currentUser != null}")
                        }
                    }
                    .addOnFailureListener{
                        Log.d(TAG, "In addOnFailureListener -- Google account deletion failed...")
                    }
            }catch (e: Exception){
                Log.d(TAG, "deleteGoogleUser() Exception: ${e.message}")
            }
        }else{
            Log.d(TAG, "No active user found when deleteGoogleUser() was called..")
        }
    }
}