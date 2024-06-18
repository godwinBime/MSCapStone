package com.example.data

data class SignUpPageUIState (
    var firstName: String = "",
    var firstNameError: Boolean = false,

    var lastName: String = "",
    var lastNameError: Boolean = false,

    var email: String = "",
    var emailError: Boolean = false,

    var phoneNumber: String = "",
    var phoneNumberError: Boolean = false,

    var password: String = "",
    var passwordError: Boolean = false,

    var confirmPassword: String = "",
    var confirmPasswordError: Boolean = false,

    var verificationCode: String = "",
    var verificationCodeError: Boolean = false
)