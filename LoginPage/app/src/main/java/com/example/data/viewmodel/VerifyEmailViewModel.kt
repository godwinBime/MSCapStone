package com.example.data.viewmodel

import android.os.IBinder.DeathRecipient
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
    private val generatedVerificationCode = generateVerificationCode()
    var sentOTPCode by mutableStateOf("")
    var verificationMessage by mutableStateOf("")
    var isOTPSent by mutableStateOf(false)
    var isOTPCodeCorrect by mutableStateOf(false)

    var emailAddress by mutableStateOf("")


    private fun generateVerificationCode(): String{
        val char = ('0'..'9').toList()
        return(1..5).map {
            char.random()
        }.joinToString("")
    }

    fun sendOTPEmail(email: EmailVerifyUIState, type: String = "None", navController: NavHostController){
//        val email = EmailVerifyUIState("")
        Log.d(TAG, "Email-Address ---> $emailAddress")
        val emailData = hashMapOf(
            "to" to emailAddress,
            "message" to hashMapOf(
                "subject" to email.subject,
                "text" to email.body + " " + generatedVerificationCode + "\n" + email.codeExpiration + "\n" + email.motivation + "\n" + email.sincerely + "\n" + email.capstoneTeam
            )
        )

        if (emailData.isEmpty()){
            isOTPSent = false
            verificationMessage = "Missing data, try again later."
        }else{
            firestore.collection("capstone").add(emailData)
                .addOnSuccessListener {
                    isOTPSent = true
                    Log.d(TAG, "isOTPSent from sendOTPEmail function: $isOTPSent")
                    verificationMessage = "Success: MFA code sent to $emailAddress"
                    Log.d(TAG, verificationMessage)
                    when(type){
                        "ChangePasswordVerifyEmail" ->{
                            Log.d(TAG, "Going to verify MFA code...")
                            navController.navigate(Routes.ChangePasswordVerifyEmail.route)
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
        if (sentOTPCode.isEmpty()){
            verificationMessage = "Please enter the verification code sent to your email"
            Log.d(TAG, verificationMessage)
        }else if (auth.currentUser != null){
            if (sentOTPCode == generatedVerificationCode){
                isOTPCodeCorrect = true
                when(destination) {
                    "MFAVerifyEmail" -> {
                        isOTPSent = false
                        navController.navigate(Routes.Home.route)
                    }
                }
            }else{
                isOTPCodeCorrect = false
                verificationMessage = "Verification code is incorrect..."
                Log.d(TAG, verificationMessage)
            }
        }
//
    }
}