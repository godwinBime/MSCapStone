package com.example.data.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.data.uistate.SignUpPageUIState
import com.example.navigation.Routes
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class UpdateProfileViewModel: ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val TAG = UpdateProfileViewModel::class.simpleName
    private var signUpPageUIState = mutableStateOf(SignUpPageUIState())
    var oldPassword by mutableStateOf("")
    var newPassword by mutableStateOf("")
    var updatedFirstName by mutableStateOf("")
    var updatedLastName by mutableStateOf("")
    var updatedPhoneNumber by mutableStateOf("")
    var updatedEmail by mutableStateOf("")

    fun updateUserProfile(navController: NavHostController){
        Log.d(TAG, "To be updated First name: $updatedFirstName")
        Log.d(TAG, "To be updated Last name: $updatedLastName")
        Log.d(TAG, "To be updated Phone number: $updatedPhoneNumber")
//        updateProfile(
//            signUpPageUIState.value.firstName,
//            signUpPageUIState.value.lastName,
//            signUpPageUIState.value.phoneNumber,
////            signUpPageUIState.value.email,
//            navController = navController
//        )
    }

    private fun updateProfile(firstName: String, lastName: String,
                              phoneNumber: String, email: String = "",
                              navController: NavHostController){
        val userId = auth.currentUser?.uid
        val updateUserData = mapOf(
            "firstName" to firstName,
            "lastName" to lastName,
            "phoneNumber" to phoneNumber
//            "email" to email
        )
//        val updateUserData = UpdateUserDataUIState(
//            firstName = firstName,
//            lastName = lastName,
//            phoneNumber = phoneNumber,email = email
//        )
        if (auth.currentUser != null){
            viewModelScope.launch {
                try {
                    val updatedDocument =
                        userId?.let { firestore.collection("userdata").document(it) }
                    updatedDocument?.update(updateUserData)?.await()
//                    navController.navigate(Routes.UserProfile.route)
//                    "9raWggrn2DcyE90ek55cn6ca3Z72"?.let {
                    if (userId != null) {
                        firestore.collection("userdata")
                            .document(userId).update(updateUserData)
                            .addOnSuccessListener {
                                Log.d(TAG, "updated successfully...")
                                Log.d(TAG, "updated First name: $firstName")
                                Log.d(TAG, "updated Last name: $lastName")
                                Log.d(TAG, "updated Phone number: $phoneNumber")
                //                                Log.d(TAG, "updated email: $email")
                                navController.navigate(Routes.UserProfile.route)
                            }
                            .addOnFailureListener{
                                Log.d(TAG, "updateProfile failed...")
                            }
                    }else{
                        Log.d(TAG, "Error from updateProfile(): No userID detected...")
                    }
                }catch (e: Exception){
                    Log.d(TAG, "updateProfile Exception: ${e.message}")
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

        }
    }

    private fun changePassword(navController: NavHostController,
                               callback: (Boolean, String) -> Unit){
        val user = auth.currentUser
        Log.d(TAG, "Credentials received:\nEmail: ${user?.email} \nOld Password: $oldPassword New password: $newPassword")
        user?.let {
            //  Re-authenticate user before changing password
            val credential = EmailAuthProvider.getCredential(user.email!!, oldPassword)
            it.reauthenticate(credential)
                .addOnCompleteListener{task ->
                    if (task.isSuccessful){
                        // if re-authentication is successful, update password
                        user.updatePassword(newPassword)
                            .addOnCompleteListener{updateTask ->
                                if (updateTask.isSuccessful){
                                    Log.d(TAG, "Password change successful")
                                    callback(true, "Password change successful")
                                    navController.navigate(Routes.UserProfile.route)
                                }else{
                                    Log.d(TAG, "Password change failed")
                                    callback(false, "Password change failed")
                                }
                            }
                    }else{
                        Log.d(TAG, "re-authentication failed...")
                        callback(false, "re-authentication failed...")
                    }
                }
        }?: run {
            Log.d(TAG, "No active user found when changePassword() was called..")
            callback(false, "No user is logged in")
        }

    }
}