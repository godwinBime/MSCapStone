package com.example.data

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.data.rules.SignUpPageValidator

class SignUpPageViewModel: ViewModel() {
    private val TAG = SignUpPageViewModel::class.simpleName
    var signUpPageUIState = mutableStateOf(SignUpPageUIState())

    var firstNameValidationsPassed = mutableStateOf(false)
    var lastNameValidationsPassed = mutableStateOf(false)
    var emailValidationsPassed = mutableStateOf(false)
    var phoneNumberValidationsPassed = mutableStateOf(false)
    var passwordValidationsPassed = mutableStateOf(false)
    var confirmPasswordValidationsPassed = mutableStateOf(false)
    var verificationCodeValidationsPassed = mutableStateOf(false)

    fun onSignUpEvent(signUpEvent: SignUpPageUIEvent){
//        validateSignUpDataWithRules()
        when(signUpEvent){
            is SignUpPageUIEvent.FirstNameChanged -> {
                validateFirstNameDataWithRules()
                signUpPageUIState.value = signUpPageUIState.value.copy(
                    firstName = signUpEvent.firstName
                )
                printSignUpState("firstName")
            }
            is SignUpPageUIEvent.LastNameChanged -> {
                validateLastNameDataWithRules()
                signUpPageUIState.value = signUpPageUIState.value.copy(
                    lastName = signUpEvent.lastName
                )
                printSignUpState("lastName")
            }

            is SignUpPageUIEvent.EmailChanged -> {
                validateEmailDataWithRules()
                signUpPageUIState.value = signUpPageUIState.value.copy(
                    email = signUpEvent.email
                )
                printSignUpState("email")
            }

            is SignUpPageUIEvent.PhoneNumberChanged -> {
                validatePhoneNumberDataWithRules()
                signUpPageUIState.value = signUpPageUIState.value.copy(
                    phoneNumber = signUpEvent.phoneNumber
                )
                printSignUpState("phoneNumber")
            }

            is SignUpPageUIEvent.PasswordChanged -> {
                validatePasswordDataWithRules()
                signUpPageUIState.value = signUpPageUIState.value.copy(
                    password = signUpEvent.password
                )
                printSignUpState("password")
            }

            is SignUpPageUIEvent.ConfirmPasswordChanged -> {
                validateConfirmPasswordDataWithRules()
                signUpPageUIState.value = signUpPageUIState.value.copy(
                    confirmPassword = signUpEvent.confirmPassword
                )
                printSignUpState("ConfirmPassword")
            }

            is SignUpPageUIEvent.VerificationCodeChanged -> {
                validateVerificationCodeDataWithRules()
                signUpPageUIState.value = signUpPageUIState.value.copy(
                    verificationCode = signUpEvent.verificationCode
                )
                printSignUpState("code_verification")
            }

            is SignUpPageUIEvent.RegisterButtonClicked -> {
                signUp()
            }
        }
    }

    private fun validateFirstNameDataWithRules(){
        val firstNameResult = SignUpPageValidator.validateFirstName(
            firstName = signUpPageUIState.value.firstName
        )
        Log.d(TAG, "FN = $firstNameResult")
        signUpPageUIState.value = signUpPageUIState.value.copy(
            firstNameError = firstNameResult.signUpStatus)

        firstNameValidationsPassed.value = firstNameResult.signUpStatus
    }

    private fun validateLastNameDataWithRules(){
        val lastNameResult = SignUpPageValidator.validateLastName(
            lastName = signUpPageUIState.value.lastName
        )
        Log.d(TAG, "LN = $lastNameResult")
        signUpPageUIState.value = signUpPageUIState.value.copy(
            lastNameError = lastNameResult.signUpStatus
        )
        lastNameValidationsPassed.value = lastNameResult.signUpStatus
    }

    private fun validateEmailDataWithRules(){
        val emailResult = SignUpPageValidator.validateEmail(
            email = signUpPageUIState.value.email
        )
        Log.d(TAG, "Email = $emailResult")
        signUpPageUIState.value = signUpPageUIState.value.copy(
            emailError = emailResult.signUpStatus
        )
        emailValidationsPassed.value = emailResult.signUpStatus
    }

    private fun validatePhoneNumberDataWithRules(){
        val phoneNumberResult = SignUpPageValidator.validatePhoneNumber(
            phoneNumber = signUpPageUIState.value.phoneNumber
        )
        Log.d(TAG, "Phone Number = $phoneNumberResult")
        signUpPageUIState.value = signUpPageUIState.value.copy(
            phoneNumberError = phoneNumberResult.signUpStatus
        )
        phoneNumberValidationsPassed.value = phoneNumberResult.signUpStatus
    }

    private fun validatePasswordDataWithRules(){
        val passwordResult = SignUpPageValidator.validatePassword(
            password = signUpPageUIState.value.password
        )
        Log.d(TAG, "Password = $passwordResult")
        signUpPageUIState.value = signUpPageUIState.value.copy(
            passwordError = passwordResult.signUpStatus
        )
        passwordValidationsPassed.value = passwordResult.signUpStatus
    }

    private fun validateConfirmPasswordDataWithRules(){
        val confirmPasswordResult = SignUpPageValidator.validateConfirmPassword(
            confirmPassword = signUpPageUIState.value.confirmPassword
        )
        Log.d(TAG, "Confirm Password = $confirmPasswordResult")
        signUpPageUIState.value = signUpPageUIState.value.copy(
            confirmPasswordError = confirmPasswordResult.signUpStatus
        )

        confirmPasswordValidationsPassed.value = confirmPasswordResult.signUpStatus
    }

    private fun validateVerificationCodeDataWithRules(){
        val verificationCodeResult = SignUpPageValidator.validateVerificationCode(
            verificationCode = signUpPageUIState.value.verificationCode
        )
        Log.d(TAG, "Verification Code = $verificationCodeResult")
        signUpPageUIState.value = signUpPageUIState.value.copy(
            verificationCodeError = verificationCodeResult.signUpStatus
        )
        verificationCodeValidationsPassed.value = verificationCodeResult.signUpStatus
    }

    private fun printSignUpState(value: String){
        Log.d(TAG, "Inside_${value}_printState")
        Log.d(TAG, signUpPageUIState.value.toString())
    }

    private fun signUp(){
        Log.d(TAG, "SignUp Button Clicked...")
        printSignUpState("button_clicked")
//        validateSignUpDataWithRules()
    }
}