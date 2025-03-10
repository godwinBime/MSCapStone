package com.example.data.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.data.uistate.EmailVerifyUIState
import com.example.data.uistate.OTPCode
import com.example.navigation.Routes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class VerifyEmailViewModel: ViewModel() {
    val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val TAG = VerifyEmailViewModel::class.simpleName
    var sentOTPCode by mutableStateOf("")
    private var verificationMessage by mutableStateOf("")
    var isOTPSent by mutableStateOf(false)
    var emailAddress by mutableStateOf("")
    private var otpCode by mutableStateOf("")
    var errorMessage by mutableStateOf("")
    var authenticationInProgress = mutableStateOf(false)

    private fun generateVerificationCode(): String{
        val char = ('0'..'9').toList()
        return (1..5).map {
                char.random()
            }.joinToString("")
    }

    fun passwordResetLink(email: String, navController: NavHostController){
        authenticationInProgress.value = true
        viewModelScope.launch {
            sendPasswordResetLink(email = email, navController = navController){
            }
        }
    }

    /**
     * Check if forgot password provided email exist in Firebase db
     */
    private fun sendPasswordResetLink(email: String, navController: NavHostController,
                                        callback: (Boolean) -> Unit){
        try {
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener{task ->
                    if (task.isSuccessful){
                        navController.navigate(Routes.ContinueToPasswordChange.route)
                        Log.d(TAG, "Email ($email) Exist")
                        callback(true)
                        authenticationInProgress.value = false
                    }else{
                        if (task.exception is FirebaseAuthInvalidUserException) {
                            Log.d(TAG, "Error: Email $email does not exist.")
                            callback(false)
                        }else{
                            Log.d(TAG, "Error: Email $email is bad.")
                            callback(false)
                        }
                    }
                }
                .addOnFailureListener{
                    callback(false)
                }
        }catch (e: Exception){
            Log.d(TAG, "sendPasswordResetEmail Exception: ${e.message}")
        }
    }

    private suspend fun readOTPCode():String?{
        var otpCode: String? = ""
        if (auth.currentUser != null) {
            authenticationInProgress.value = true
            try {
                val document =
                    firestore.collection("authuser").document("qM1Zd2xkkJSGNxGp6vyT").get().await()
                Log.d(TAG, "OTPCode from db:-> ${document.get("otpcode") as? String }")
                otpCode = document.getString("otpcode")
                authenticationInProgress.value = false
                return otpCode
            } catch (e: Exception) {
                Log.d(TAG, "readOTPCode Exception: ${e.message}")
                authenticationInProgress.value = false
                return "Exception: No value read from db by readOTPCode()"
            }
        }else{
            Log.d(TAG, "No active user found when readOTPCode() was called..")
            return "No value read from db by readOTPCode()"
        }
    }

    private fun otpCodeUpdate(context: Context){
        viewModelScope.launch {
            authenticationInProgress.value = true
            updateOTPCode(context = context)
        }
    }

    private fun updateOTPCode(context: Context){
        if(auth.currentUser != null) {
//            if (!isOTPGenerated(context = context)) {
//                saveOTPCode(context = context, otpCode = generateVerificationCode())
//                setOTPGeneratedFlag(context = context)
//            }else{
//                Log.d(TAG, "OTP code is generated")
//            }
            viewModelScope.launch {
                try {
//                    val otpCodeSharedPref = getOTPCode(context = context)
                    val documentRef =
                        firestore.collection("authuser").document("qM1Zd2xkkJSGNxGp6vyT")
                    val updateData = hashMapOf<String, Any>(
                        "otpcode" to generateVerificationCode() // otpCodeSharedPref.toString()
                    )
                    documentRef.update(updateData).await()
                    authenticationInProgress.value = false
                    /*
                     Read otpcode from db
                val updateAndRead = documentRef.get().await()
                generatedCode = (updateAndRead.get("otpcode") as? String).toString()
                Log.d(TAG, "New OTPCode:-> ${readOTPCode()}")
                    */
                } catch (e: Exception) {
                    authenticationInProgress.value = false
                    Log.d(TAG, "updateOTPCode Exception: ${e.message}")
                }
            }
        }else {
            Log.d(TAG, "No active user found when updateOTPCode() was called..")
        }
    }

    fun sendOTPToEmail(email: EmailVerifyUIState,
                       navController: NavHostController,
                       type: String, context: Context){
        viewModelScope.launch {
            authenticationInProgress.value = true
            sendOTPEmail(
                email = email,
                type = type,
                navController = navController,
                context = context)
        }
    }

    private suspend fun sendOTPEmail(email: EmailVerifyUIState, type: String = "None",
                                     navController: NavHostController, context: Context){
//        val sharedPrefOTPCode = getOTPCode(context = context).toString()
        if (auth.currentUser == null){
            Log.d(TAG, "Password reset request initiated...")
            email.to = emailAddress
        }else{
            otpCodeUpdate(context = context)
            otpCode = readOTPCode().toString()  // sharedPrefOTPCode
        }
        val emailData = hashMapOf(
            "to" to email.to,
            "message" to hashMapOf(
                "subject" to email.subject,
                "text" to email.body + "\n\n" + otpCode + "\n\n" + email.codeExpiration + "\n" + email.motivation + "\n" + email.sincerely + "\n" + email.capstoneTeam
            )
        )
        if (emailData.isEmpty()){
            isOTPSent = false
            verificationMessage = "Missing data, try again later."
        }else if(email.to.isNotEmpty()){
            otpCode = readOTPCode().toString() /*sharedPrefOTPCode */
            firestore.collection("capstone").add(emailData)
                .addOnSuccessListener {
                    isOTPSent = true
                    Log.d(TAG, "isOTPSent ($otpCode) from sendOTPEmail function: $isOTPSent")
                    when(type){
                        "ChangePasswordVerifyEmail" ->{
                            Log.d(TAG, "ChangePasswordVerifyEmail code sent...")
                            navController.navigate(Routes.ChangePasswordVerifyEmail.route)
                        }
                        "MFAVerifyEmail" ->{
                            Log.d(TAG, "MFAVerifyEmail code sent to ${email.to}")
                            navController.navigate(Routes.MFAVerifyEmail.route)
                        }
                        "DeleteProfile" ->{
                            Log.d(TAG, "DeleteProfile code sent to ${email.to}")
                            navController.navigate(Routes.DeleteProfileVerifyEmail.route)
                        }
                        "ResendOTP" -> {
                            Log.d(TAG, "OTP code resent to ${email.to}")
                        }
                    }
                    authenticationInProgress.value = false
                }
                .addOnFailureListener{
                    isOTPSent = false
                    verificationMessage = "Error: -> MFA code not sent"
                    Log.d(TAG, verificationMessage)
                }
        }else{
            verificationMessage = "Error: -> Issue with email or code to sendOTPEmail. Try again..."
            Log.d(TAG, verificationMessage)
        }
    }

    fun resetOtpCode(){
        sentOTPCode = ""
    }

    fun verifySentOTPCode(navController: NavHostController,
                          destination: String = "None", context: Context){
        viewModelScope.launch {
            verifyOTPCode(navController = navController,
                destination = destination, context = context)
        }
    }

    private suspend fun verifyOTPCode(navController: NavHostController,
                                      destination: String = "None", context: Context){
        authenticationInProgress.value = true
        Log.d(TAG, verificationMessage)
        if (sentOTPCode.isEmpty()){
            verificationMessage = "Please enter the verification code sent to your email"
            Log.d(TAG, verificationMessage)
        }else if (auth.currentUser != null){
            if (sentOTPCode == readOTPCode() /* getOTPCode(context = context) */){
                when(destination) {
                    "VerifyAndGotoHomeScreen" -> {
                        isOTPSent = false
                        verificationMessage = "OTP Verified...Navigating to home screen..."
                        Log.d(TAG, verificationMessage)
                        authenticationInProgress.value = false
                        navController.navigate(Routes.Home.route)
                    }
                    "ChangePasswordVerifyEmail" -> {
                        isOTPSent = false
                        verificationMessage = "Logged-in User Password change request otp verified..."
                        Log.d(TAG, verificationMessage)
                        authenticationInProgress.value = false
                        navController.navigate(Routes.NewPassword.route)
                    }
                    "DeleteProfile" -> {
                        isOTPSent = false
                        verificationMessage = "Logged-in User account deletion request otp verified..."
                        Log.d(TAG, verificationMessage)
                        authenticationInProgress.value = false
                        navController.navigate(Routes.DeleteProfile.route)
                    }
                }
//                resetOTPCode(context = context)
            }else{
                errorMessage = "Error! wrong OTP code entered."
                verificationMessage = "Error: Verification code ($sentOTPCode) is incorrect...\nExpected code from readOTPCode() ${readOTPCode()}"
                Log.d(TAG, verificationMessage)
            }
        }
    }

    /**
     * Sharedpreference to save OTP code
     */
    /*private fun saveOTPCode(context: Context, otpCode: String){
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("otpCode", otpCode)
        editor.apply()
    }

    fun getOTPCode(context: Context): String?{
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("otpCode", null)
    }

    private fun setOTPGeneratedFlag(context: Context){
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("otpGenerated", true)
        editor.apply()
    }

    private fun isOTPGenerated(context: Context): Boolean{
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("otpGenerated", false)
    }

    fun resetOTPCode(context: Context){
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("otpCode")
        editor.putBoolean("otpGenerated", false)
        Log.d(TAG, "Resetting OTP data...")
        editor.apply()
    }
    */
}