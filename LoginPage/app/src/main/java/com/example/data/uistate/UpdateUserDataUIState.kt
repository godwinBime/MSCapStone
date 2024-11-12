package com.example.data.uistate

import android.net.Uri

data class UpdateUserDataUIState(
//    var userId: String? = "",
    var firstName: String = "",
    var lastName: String = "",
    var phoneNumber: String = ""
//    var email: String = " "
)

data class UpdatePasswordData(
    var oldPassword: String = "",
    var newPassword: String = ""
)

data class UserProfilePictureData(
    var userProfilePictureDataImageUri: Uri? = null
)