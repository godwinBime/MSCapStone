package com.example.data.uistate

data class EmailVerifyUIState(
    var to: String,
    val subject: String = "Confirm Your Identity",
    var body: String = "Thank you for using Capstone App. When prompted, use the following verification code: ",
    val codeExpiration: String = "The code will expire in 10 minutes.",
    val capstoneTeam: String = "The Capstone App Team.",
    val motivation: String = "Enjoy using the Capstone App.",
    val sincerely: String = "sincerely,"
)