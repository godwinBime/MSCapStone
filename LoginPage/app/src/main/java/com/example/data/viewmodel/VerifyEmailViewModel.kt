package com.example.data.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.data.uistate.EmailVerifyUIState
import com.example.navigation.Routes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class VerifyEmailViewModel: ViewModel() {
    val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val TAG = VerifyEmailViewModel::class.simpleName
    var sentOTPCode by mutableStateOf("")
    var verificationMessage by mutableStateOf("")
    var isOTPSent by mutableStateOf(false)
    var emailAddress by mutableStateOf("")
//    var generatedCode by mutableStateOf(generateVerificationCode())
    var isOTPCodeGenerated = false


    private fun generateVerificationCode(): String{
        val char = ('0'..'9').toList()
        return (1..5).map {
                char.random()
            }.joinToString("")
    }


    fun sendOTPEmail(email: EmailVerifyUIState, type: String = "None", navController: NavHostController){
//        val email = EmailVerifyUIState("")
//        Log.d(TAG, "Email-Address ---> $emailAddress")
//        getGeneratedCode()
        email.to = auth.currentUser?.email.toString()
        email.code = generateVerificationCode()
        val emailData = hashMapOf(
            "to" to email.to,
            "message" to hashMapOf(
                "subject" to email.subject,
                "text" to email.body + " " + email.code + "\n" + email.codeExpiration + "\n" + email.motivation + "\n" + email.sincerely + "\n" + email.capstoneTeam,
                "code" to email.code
            )
        )
        Log.d(TAG, "Code sent to ${email.to} is ${email.code}")
        if (emailData.isEmpty()){
            isOTPSent = false
            verificationMessage = "Missing data, try again later."
        }else{
            firestore.collection("capstone").add(emailData)
                .addOnSuccessListener {
                    isOTPSent = true
                    Log.d(TAG, "isOTPSent (${email.code}) from sendOTPEmail function: $isOTPSent")
                    when(type){
                        "ChangePasswordVerifyEmail" ->{
                            Log.d(TAG, "ChangePasswordVerifyEmail code sent...")
                            navController.navigate(Routes.ChangePasswordVerifyEmail.route)
                        }
                        "MFAVerifyEmail" ->{
                            Log.d(TAG, "MFAVerifyEmail code sent to ${email.to}")
                            navController.navigate(Routes.MFAVerifyEmail.route)
                        }
                    }
                }
                .addOnFailureListener{
                    isOTPSent = false
                    verificationMessage = "Error: -> MFA code not sent"
                    Log.d(TAG, verificationMessage)
                }
        }
    }

    fun verifyOTPCode(navController: NavHostController, destination: String = "None"){
        val email = EmailVerifyUIState("")
        if (sentOTPCode.isEmpty()){
            verificationMessage = "Please enter the verification code sent to your email"
            Log.d(TAG, verificationMessage)
        }else if (auth.currentUser != null){
            if (sentOTPCode == email.code){
                Log.d(TAG, verificationMessage)
                when(destination) {
                    "VerifyAndGotoHomeScreen" -> {
                        isOTPSent = false
                        verificationMessage = "OTP Verified...Navigating to home screen..."
                        navController.navigate(Routes.Home.route)
                    }
                }
            }else{
                verificationMessage = "Error: Verification code ($sentOTPCode) is incorrect...\nExpected code ${email.code}"
                Log.d(TAG, verificationMessage)
            }
        }
//
    }
}