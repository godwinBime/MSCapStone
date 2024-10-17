package com.example.data.uistate

import com.google.firebase.auth.FirebaseAuth

private val auth = FirebaseAuth.getInstance()
data class EmailVerifyUIState(
    var to: String = auth.currentUser?.email.toString(),
    val subject: String = "Confirm Your Identity",
    var body: String = "Thank you for using Capstone App. \nWhen prompted, use the following verification code: ",
    val codeExpiration: String = "The code will expire in 10 minutes.",
    val capstoneTeam: String = "The Capstone App Team.",
    val motivation: String = "Enjoy using the Capstone App.",
    val sincerely: String = "sincerely,"
)

data class OTPCode(
    var id: String = "1",
    var otpcode: String = ""
)