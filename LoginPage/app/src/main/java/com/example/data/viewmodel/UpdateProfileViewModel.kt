package com.example.data.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.data.uistate.SignUpPageUIState

class UpdateProfileViewModel: ViewModel() {
    var signUpPageUIState = mutableStateOf(SignUpPageUIState())

    fun updateUserProfile(){
        updateProfile(
            signUpPageUIState.value.firstName,
            signUpPageUIState.value.lastName,
            signUpPageUIState.value.phoneNumber,
            signUpPageUIState.value.email
        )
    }

    private fun updateProfile(firstName: String, lastName: String,
                              phoneNumber: String, email: String){

    }
}