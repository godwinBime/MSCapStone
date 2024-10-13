package com.example.data.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.navigation.Routes
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class UpdateProfileViewModel: ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val TAG = SignUpPageViewModel::class.simpleName

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
        viewModelScope.launch {
            updateProfile(
                firstName = updatedFirstName,
                lastName = updatedLastName,
                phoneNumber = updatedPhoneNumber,
                navController = navController
            )
        }
//        updateProfile(
//            signUpPageUIState.value.firstName,
//            signUpPageUIState.value.lastName,
//            signUpPageUIState.value.phoneNumber,
//            signUpPageUIState.value.email,
//            navController = navController
//        )
    }

    fun getDocumentID():String{
        val userId = auth.currentUser?.uid
        var documentID = "empty-doc-id"
        if (auth.currentUser != null){
            val query = firestore.collection("userdata").whereEqualTo("userId", userId)
            query.get()
                .addOnSuccessListener { querySnapShot ->
                    if (!querySnapShot.isEmpty){
                        val documentSnapShot = querySnapShot.documents[0]
                        documentID = documentSnapShot.id
                        Log.d(TAG, "DocumentId inside getDocumentID(): $documentID")
                    }else{
                        Log.d(TAG, "No document ID Found...")
                    }
                }
        }
        return documentID
    }

    private suspend fun updateProfile(firstName: String, lastName: String,
                              phoneNumber: String, email: String = "",
                              navController: NavHostController){
//        val updateUserData = UpdateUserDataUIState(
//            firstName = firstName,
//            lastName = lastName,
//            phoneNumber = phoneNumber
//        )
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