package com.example.data.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.data.uistate.UserProfilePictureData
import com.example.navigation.Routes
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ProfileViewModel: ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val storage: FirebaseStorage = Firebase.storage
    private val TAG = ProfileViewModel::class.simpleName
    var message by mutableStateOf("")
    val profilePictureExist : MutableLiveData<Boolean> = MutableLiveData(false)
    private val isUploadSuccessful : MutableLiveData<Boolean> = MutableLiveData(false)
    private val isDownloadSuccessful : MutableLiveData<Boolean> = MutableLiveData(false)
    private val _profilePictureUri = MutableLiveData<UserProfilePictureData>()
    val profilePictureUri: LiveData<UserProfilePictureData> get() = _profilePictureUri
    private val _uploadProgress = MutableLiveData<Float>()
    val uploadProgress: LiveData<Float> get() = _uploadProgress
    private val _uploadStatus = MutableLiveData<String>()
//    val uploadStatus: LiveData<String> get() = _uploadStatus

    var updateProfileInProgress = mutableStateOf(false)
    var oldPassword by mutableStateOf("")
    var newPassword by mutableStateOf("")
    var updatedFirstName by mutableStateOf("")
    var updatedLastName by mutableStateOf("")
    var updatedPhoneNumber by mutableStateOf("")

    init {
        _profilePictureUri.value = UserProfilePictureData()  // Initialize with a default value
    }

    fun updateUserProfile(navController: NavHostController) {
        Log.d(TAG, "To be updated First name: $updatedFirstName")
        Log.d(TAG, "To be updated Last name: $updatedLastName")
        Log.d(TAG, "To be updated Phone number: $updatedPhoneNumber")
        updateProfileInProgress.value = true
        viewModelScope.launch {
            updateProfile(
                firstName = updatedFirstName,
                lastName = updatedLastName,
                phoneNumber = updatedPhoneNumber,
                navController = navController
            )
        }
    }

    private fun updateProfile(firstName: String, lastName: String,
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
                                                updateProfileInProgress.value = false
                                                Log.d(TAG, "Success in Updating user data...")
                                                navController.navigate(Routes.Home.route)
                                            }
                                            .addOnFailureListener{
                                                updateProfileInProgress.value = false
                                                Log.d(TAG, "Failed to Update user data...")
                                            }
                                    }
                                }
                                updateProfileInProgress.value = false
                            }
                            .addOnFailureListener{
                                Log.d(TAG, "Failed to find documentID...")
                                updateProfileInProgress.value = false
                            }
                    }else{
                        Log.d(TAG, "Error from updateProfile(): No userID detected...")
                        updateProfileInProgress.value = false
                    }
                }catch (e: Exception){
                    Log.d(TAG, "updateProfile Exception: ${e.message}")
                    updateProfileInProgress.value = false
                }
            }
        }else{
            Log.d(TAG, "No active user found when updateProfile() was called..")
            updateProfileInProgress.value = false
        }
    }

    fun changeUserPassword(navController: NavHostController){
        updateProfileInProgress.value = true
        changePassword(
            navController = navController
        ){ _, _ ->
            updateProfileInProgress.value = true
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
                            updateProfileInProgress.value = false
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
                            updateProfileInProgress.value = false
                            Log.d(TAG, "re-authentication failed...")
                            callback(false, "re-authentication failed...")
                        }
                        updateProfileInProgress.value = false
                    }
                    .addOnFailureListener{
                        updateProfileInProgress.value = false
                        callback(false, "re-authentication failed...")
                    }
            } ?: run {
                updateProfileInProgress.value = false
                Log.d(TAG, "No active user found when changePassword() was called..")
                callback(false, "No user is logged in")
            }
        }catch (e: Exception){
            updateProfileInProgress.value = false
            Log.d(TAG, "changePassword() Exception was triggered.. ${e.message}")
        }
    }

    fun deleteProfilePicture(imagePath: String?,
                             action: String = "None",
                             homeViewModel: HomeViewModel,
                             signUpPageViewModel: SignUpPageViewModel,
                             context: Context,
                             navController: NavHostController){
        updateProfileInProgress.value = true
        deletePicture(imagePath = imagePath,
            action = action,
            homeViewModel = homeViewModel,
            signUpPageViewModel = signUpPageViewModel,
            context = context,
            navController = navController)
    }

    private fun deletePicture(imagePath: String?,
                              action: String = "None",
                              homeViewModel: HomeViewModel,
                              signUpPageViewModel: SignUpPageViewModel,
                              context: Context,
                              navController: NavHostController){
        val storageRef = storage.reference
        val desertRef = imagePath?.let { storageRef.child(it) }
        desertRef?.delete()
            ?.addOnSuccessListener {
                updateProfileInProgress.value = false
                Log.d(TAG, "Image successfully deleted...")
                when(action){
                    "DeleteAccount" -> {
                        homeViewModel.logOut(navController = navController,
                            signUpPageViewModel = signUpPageViewModel,
                            context = context)
                        navController.navigate(Routes.Login.route)
                    }
                    "DefaultProfilePicture" -> {
                        navController.navigate(Routes.UserProfile.route)
                    }
                }
            }
            ?.addOnFailureListener{
                updateProfileInProgress.value = false
                Log.d(TAG, "Image not deleted...")
            }
    }

    fun deleteCurrentProfile(navController: NavHostController,
                             signUpPageViewModel: SignUpPageViewModel,
                             homeViewModel: HomeViewModel,
                             context: Context,
                             providerId: String){
        updateProfileInProgress.value = true
        if (providerId == "password") {
            deleteProfile(navController = navController,
                signUpPageViewModel = signUpPageViewModel,
                context = context,
                homeViewModel = homeViewModel,
                userType = providerId)
        }
    }

    private fun deleteUsernamePassword(navController: NavHostController,
                                       signUpPageViewModel: SignUpPageViewModel,
                                       homeViewModel: HomeViewModel,
                                       context: Context,
                                       userType: String){
        val user = auth.currentUser
        val imagePath = "/ProfilePictures/${user?.uid}"
        try{
            if (user != null){
                Log.d(TAG, "About to delete logged-in user...")
                user.delete()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "Email and password deleted successfully...")
                            if (userType == "password") {
                                deleteProfilePicture(imagePath = imagePath,
                                    action = "DeleteAccount",
                                    homeViewModel = homeViewModel,
                                    signUpPageViewModel = signUpPageViewModel,
                                    context = context,
                                    navController = navController)
                            } else if (userType == "google.com") {
                                Log.d(TAG, "Google account user deleted, no data to delete")
                                navController.navigate(Routes.Login.route)
                            } else {
                                Log.d(TAG, "Error: Undefined user...")
                            }
                        }
                        updateProfileInProgress.value = false
                    }
                    .addOnFailureListener{
                        updateProfileInProgress.value = false
                        Log.d(TAG, "Error: addOnFailureListener:-> Email and password not deleted...")
                    }
            }else{
                updateProfileInProgress.value = false
                Log.d(TAG, "Error: No logged-in user found when deleteUsernamePassword() was called...")
            }
        }catch (e: Exception){
            updateProfileInProgress.value = false
            Log.d(TAG, "deleteUsernamePassword Exception: ${e.message}")
        }
    }

    private fun deleteProfile(navController: NavHostController,
                              signUpPageViewModel: SignUpPageViewModel,
                              context: Context,
                              homeViewModel: HomeViewModel,
                              userType: String){
        if (auth.currentUser != null) {
            updateProfileInProgress.value = true
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
                                                    homeViewModel = homeViewModel,
                                                    context = context,
                                                    userType = userType
                                                )
                                            }else{
                                                message = "Token expired, re-authenticate and try again."
                                                Log.d(TAG, "Call from deleteProfile(): task to delete user profile failed")
                                            }
                                            updateProfileInProgress.value = false
                                        }
                                        .addOnFailureListener{
                                            message = "Token expired, re-authenticate and try again."
                                            Log.d(TAG, "addOnFailureListener Call from deleteProfile(): task to delete user profile failed")
                                        }
                                }
                            }else if (auth.currentUser != null){
                                Log.d(TAG, "Call from deleteProfile()...Logged-in user with no data...")
                                /*
                                deleteUsernamePassword(
                                    navController = navController,
                                    signUpPageViewModel = signUpPageViewModel,
                                    userType = userType
                                )
                                */
                            }else{
                                Log.d(TAG, "Error: DocumentId Not found")
                            }
                        }
                }catch (e: Exception){
                    Log.d(TAG, "deleteUser Exception: ${e.message}")
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
        updateProfileInProgress.value = true
        if (providerId == "google.com"){
            viewModelScope.launch {
                deleteGoogleUser(navController = navController, providerId = providerId,
                    context = context, homeViewModel = homeViewModel,
                    signUpPageViewModel = signUpPageViewModel)
            }
        }
    }
/*
    private fun refreshToken(context: Context){
        val user = auth.currentUser
        try {
            user?.getIdToken(true)?.addOnCompleteListener { newTokenTask ->
                if (newTokenTask.isSuccessful) {
                    Log.d(TAG, "refreshToken() New token obtained...")
                    val account = GoogleSignIn.getLastSignedInAccount(context)
                    val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
//                    val credential = GoogleAuthProvider.getCredential(newTokenTask.result?.token, null)
                    user.reauthenticate(credential)
                    Log.d(TAG, "reAuthenticated with New token...")
                }
            }
                ?.addOnFailureListener {
                    Log.d(TAG, "Failed to get new token...")
                }
        }catch (e: Exception){
            Log.d(TAG, "refreshToken() Exception: ${e.message}")
        }
    }*/

    private suspend fun deleteGoogleUser(navController: NavHostController,
                                         providerId: String,
                                         context: Context,
                                         homeViewModel: HomeViewModel,
                                         signUpPageViewModel: SignUpPageViewModel){
        val user = auth.currentUser
        if (user != null && user.providerData.any{it.providerId == "google.com"}){
            val account = GoogleSignIn.getLastSignedInAccount(context)
            val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
            try {
                // Re-authenticate user
                user.reauthenticate(credential).await()
                user.delete()
                    .addOnSuccessListener {
                        updateProfileInProgress.value = false
//                        homeViewModel.checkForActiveSession()
                        homeViewModel.logOut(navController = navController,
                            signUpPageViewModel = signUpPageViewModel,
                            context = context)
                        navController.navigate(Routes.Login.route)
                        if (auth.currentUser == null) {
                            Log.d(TAG, "Google account successfully deleted...")
                        }
                        Log.d(TAG, "Any active user after account deletion?: ${auth.currentUser != null}")

                    }
                    .addOnFailureListener{
                        updateProfileInProgress.value = false
                        message = "Token expired, re-authenticate and try again."
                        Log.d(TAG, "In addOnFailureListener -- Google account deletion failed...")
                    }
            }catch (e: Exception){
                updateProfileInProgress.value = false
                message = "Token expired, re-authenticate and try again."
                Log.d(TAG, "deleteGoogleUser() Exception: ${e.message}")
            }
        }else{
            updateProfileInProgress.value = false
            Log.d(TAG, "No active user found when deleteGoogleUser() was called..")
        }
    }

    fun uploadProfilePicture(uri: Uri?, isCallValid: Boolean = false,
                             navController: NavHostController,
                             onSuccess: () -> Unit,
                             onFailure: (Exception) -> Unit){
        updateProfileInProgress.value = true
        uploadPicture(
            uri = uri,
            isCallValid = isCallValid,
            navController = navController, onSuccess = onSuccess, onFailure = onFailure
        )
    }

//    fun resetUploadProgress(){
//        _uploadProgress.value = 0f
//    }

    private fun uploadPicture(uri: Uri?, isCallValid: Boolean = false,
                             navController: NavHostController,
                             onSuccess: () -> Unit,
                             onFailure: (Exception) -> Unit){
        viewModelScope.launch {
            try {
                if (uri != null && auth.currentUser?.uid != null && isCallValid) {
                    val storageRef =
                        storage.reference.child("ProfilePictures/${auth.currentUser?.uid}")
                    val uploadTask = storageRef.putFile(uri)
                    uploadTask
                        .addOnSuccessListener {
                            uploadTask.cancel()
                            _uploadStatus.value = "Upload successful"
                            isUploadSuccessful.value = true
                            Log.d(TAG, "Upload Success...Path: ${storageRef.path}")
                            Log.d(TAG, "Upload Complete... ${_uploadProgress.value}%")
                            navController.navigate(Routes.UserProfile.route)
                            onSuccess()
                            downloadProfilePicture(imagePath = storageRef.path,
                                isCallValid = true, onSuccess = {
                                    onSuccess()
                                },
                                onFailure = {
                                    onFailure(it)
                                })
                        }
                        .addOnProgressListener {taskSnapshot ->
                            val progress = (100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount).toFloat()
                            Log.d(TAG, "Upload in progress... $progress%")
                            _uploadProgress.value = progress
                        }
                        .addOnFailureListener{
                            isUploadSuccessful.value = false
                            updateProfileInProgress.value = false
                            Log.d(TAG, "Upload Failed...")
                            onFailure(it)
                        }
                }else{
                    isUploadSuccessful.value = false
                    updateProfileInProgress.value = false
                    Log.d(TAG, "Upload Failed No image provided...")
                }
            }catch (e: Exception){
                isUploadSuccessful.value = false
                updateProfileInProgress.value = false
                Log.d(TAG, "Upload Exception: ${e.message}")
                onFailure(e)
                _uploadStatus.value = "Upload failed with exception: ${e.message}"
            }finally {
                Log.d(TAG, "Is Upload Success...: ${isUploadSuccessful.value}")
            }
        }

    }

    fun downloadProfilePicture(imagePath: String?, isCallValid: Boolean = false, onSuccess: (Uri) -> Unit,
                               onFailure: (Exception) -> Unit){
        Log.d(TAG,
            "Is profilePictureUri empty...: ${_profilePictureUri.value == null}"
        )
        isPictureExistInDatabase(imagePath = imagePath,
            onSuccess = onSuccess, onFailure = onFailure)

        if (_profilePictureUri.value == null){
            Log.d(TAG, "Inside downloadProfilePicture()...about to initiate download...")
            updateProfileInProgress.value = true
            downloadPicture(
                imagePath = imagePath,
                isCallValid = isCallValid, onSuccess = onSuccess, onFailure = onFailure
            )
        }
    }

    private fun downloadPicture(imagePath: String?, isCallValid: Boolean = false, onSuccess: (Uri) -> Unit,
                                       onFailure: (Exception) -> Unit){
        viewModelScope.launch {
            try {
                if (!imagePath.isNullOrEmpty() && isCallValid) {
                    val storageRef = storage.reference.child(imagePath)
                    storageRef.downloadUrl
                        .addOnSuccessListener { uri ->
                            _profilePictureUri.value = UserProfilePictureData(uri)
                            isDownloadSuccessful.value = true
                            onSuccess(uri)
                            Log.d(TAG, "Download Success...Path: ${storageRef.path}")
                            updateProfileInProgress.value = false
                        }
                        .addOnFailureListener {
                            _profilePictureUri.value = UserProfilePictureData(null)
                            isDownloadSuccessful.value = false
                            updateProfileInProgress.value = false
                            Log.d(TAG, "Call from downloadProfilePicture()...Download Failed")
                            onFailure(it)
                        }
                }else{
                    isDownloadSuccessful.value = false
                    updateProfileInProgress.value = false
                    Log.d(TAG, "Download Failed No image provided...")
                }
            }catch (e: Exception){
                isDownloadSuccessful.value = false
                updateProfileInProgress.value = false
                Log.d(TAG, "Upload Exception: ${e.message}")
                onFailure(e)
            }finally {
                Log.d(TAG, "Is Download Success...: ${isDownloadSuccessful.value}")
            }
        }
    }

    fun isPictureExistInDatabase(imagePath: String?, onSuccess: (Uri) -> Unit,
                                 onFailure: (Exception) -> Unit){
        if (!imagePath.isNullOrEmpty()){
            viewModelScope.launch {
                try {
                    updateProfileInProgress.value = true
                    val storeRef = storage.reference.child(imagePath)
                    storeRef.metadata
                        .addOnSuccessListener {
                            profilePictureExist.value = true
                            storeRef.downloadUrl
                                .addOnSuccessListener {uri ->
                                    updateProfileInProgress.value = false
                                    onSuccess(uri)
                                    Log.d(TAG, "Call from isPictureExistInDatabase()...Profile Picture exist...")
                                }
                                .addOnFailureListener{
                                    updateProfileInProgress.value = false
                                    Log.d(TAG, "Call from isPictureExistInDatabase()...Download URI error")
                                    onFailure(it)
                                }
                            updateProfileInProgress.value = false
                        }
                        .addOnFailureListener{
                            updateProfileInProgress.value = false
                            if (it.message?.contains("Object does not exist") == true) {
                                Log.d(TAG, "Call from isPictureExistInDatabase() ... Profile Picture does not exist..")
                                profilePictureExist.value = false
                                onFailure(Exception("File does not exist"))
                            }else{
                                profilePictureExist.value = false
                                onFailure(it)
                            }
                        }
                } catch (e: Exception) {
                    updateProfileInProgress.value = false
                    onFailure(e)
                }
            }
        }
    }
}