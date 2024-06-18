package com.example.data

sealed class SignUpPageUIEvent {
    data class FirstNameChanged(val firstName:String): SignUpPageUIEvent()
    data class LastNameChanged(val lastName: String): SignUpPageUIEvent()
    data class EmailChanged(val email: String): SignUpPageUIEvent()
    data class PhoneNumberChanged(val phoneNumber: String): SignUpPageUIEvent()
    data class PasswordChanged(val password: String): SignUpPageUIEvent()
    data class ConfirmPasswordChanged(val confirmPassword: String): SignUpPageUIEvent()
    data class VerificationCodeChanged(val verificationCode: String): SignUpPageUIEvent()
    object RegisterButtonClicked: SignUpPageUIEvent()
}