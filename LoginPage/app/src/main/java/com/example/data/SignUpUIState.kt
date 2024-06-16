package com.example.data

data class SignUpUIState (
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var phoneNumber: String = "",
    var password: String = "",
    var confirmPassword: String = "",
    var verificationCode: String = ""
)