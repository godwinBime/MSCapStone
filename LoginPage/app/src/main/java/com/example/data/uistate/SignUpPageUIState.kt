package com.example.data.uistate

import android.net.Uri

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
    var verificationCodeError: Boolean = false,

    var privacyPolicyAccepted: Boolean = false,
    var privacyPolicyError: Boolean = false
)

//  User data to be stored in the Firebase db
data class UserData(
    var userId: String? = "",
    var firstName: String = "",
    var lastName: String = "",
    var phoneNumber: String = "",
    var email: String = " "
//    var profilePicture: Uri? = null
)

data class ProfilePicture(
    var profilePicture: Uri? = null
)