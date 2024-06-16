package com.example.data

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {
    private val TAG = LoginViewModel::class.simpleName
    var signUpUIState = mutableStateOf(SignUpUIState())

    fun onEvent(event: UIEvent){
        when(event){
            is UIEvent.FirstNameChanged -> {
                signUpUIState.value = signUpUIState.value.copy(
                    firstName = event.firstName
                )
                printState()
            }
            is UIEvent.LastNameChanged -> {
                signUpUIState.value = signUpUIState.value.copy(
                    lastName = event.lastName
                )
                printState()
            }

            is UIEvent.EmailChanged -> {
                signUpUIState.value = signUpUIState.value.copy(
                    email = event.email
                )
                printState()
            }

            is UIEvent.PhoneNumberChanged -> {
                signUpUIState.value = signUpUIState.value.copy(
                    phoneNumber = event.phoneNumber
                )
                printState()
            }

            is UIEvent.PasswordChanged -> {
                signUpUIState.value = signUpUIState.value.copy(
                    password = event.password
                )
                printState()
            }

            is UIEvent.ConfirmPasswordChanged -> {
                signUpUIState.value = signUpUIState.value.copy(
                    confirmPassword = event.confirmPassword
                )
                printState()
            }

            is UIEvent.VerificationCodeChanged -> {
                signUpUIState.value = signUpUIState.value.copy(
                    verificationCode = event.verificationCode
                )
                printState()
            }
        }
    }

    private fun printState(){
        Log.d(TAG, "Inside_printState")
        Log.d(TAG, signUpUIState.value.toString())
    }
}