package com.example.data.uistate

data class UpdateUserDataUIState(
    var userId: String? = "",
    var firstName: String = "",
    var lastName: String = "",
    var phoneNumber: String = "",
    var email: String = " "
)

data class UpdatePasswordData(
    var oldPassword: String = "",
    var newPassword: String = ""
)