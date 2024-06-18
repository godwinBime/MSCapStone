package com.example.data

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.data.rules.SignUpPageValidator

class SignUpPageViewModel: ViewModel() {
    private val TAG = SignUpPageViewModel::class.simpleName
    var signUpPageUIState = mutableStateOf(SignUpPageUIState())

    fun onSignUpEvent(signUpEvent: SignUpPageUIEvent){
//        validateSignUpDataWithRules()
        when(signUpEvent){
            is SignUpPageUIEvent.FirstNameChanged -> {
                validateFirstName()
                signUpPageUIState.value = signUpPageUIState.value.copy(
                    firstName = signUpEvent.firstName
                )
                printSignUpState("firstName")
            }
            is SignUpPageUIEvent.LastNameChanged -> {
                validateLastName()
                signUpPageUIState.value = signUpPageUIState.value.copy(
                    lastName = signUpEvent.lastName
                )
                printSignUpState("lastName")
            }

            is SignUpPageUIEvent.EmailChanged -> {
                validateEmail()
                signUpPageUIState.value = signUpPageUIState.value.copy(
                    email = signUpEvent.email
                )
                printSignUpState("email")
            }

            is SignUpPageUIEvent.PhoneNumberChanged -> {
                validatePhoneNumber()
                signUpPageUIState.value = signUpPageUIState.value.copy(
                    phoneNumber = signUpEvent.phoneNumber
                )
                printSignUpState("phoneNumber")
            }

            is SignUpPageUIEvent.PasswordChanged -> {
                validatePassword()
                signUpPageUIState.value = signUpPageUIState.value.copy(
                    password = signUpEvent.password
                )
                printSignUpState("password")
            }

            is SignUpPageUIEvent.ConfirmPasswordChanged -> {
                validateConfirmPassword()
                signUpPageUIState.value = signUpPageUIState.value.copy(
                    confirmPassword = signUpEvent.confirmPassword
                )
                printSignUpState("ConfirmPassword")
            }

            is SignUpPageUIEvent.VerificationCodeChanged -> {
                validateVerificationCode()
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

    private fun validateFirstName(){
        val firstNameResult = SignUpPageValidator.validateFirstName(
            firstName = signUpPageUIState.value.firstName
        )
        Log.d(TAG, "FN = $firstNameResult")
        signUpPageUIState.value = signUpPageUIState.value.copy(
            firstNameError = firstNameResult.signUpStatus)
    }

    private fun validateLastName(){
        val lastNameResult = SignUpPageValidator.validateLastName(
            lastName = signUpPageUIState.value.lastName
        )
        Log.d(TAG, "LN = $lastNameResult")
        signUpPageUIState.value = signUpPageUIState.value.copy(
            lastNameError = lastNameResult.signUpStatus
        )
    }

    private fun validateEmail(){
        val emailResult = SignUpPageValidator.validateEmail(
            email = signUpPageUIState.value.email
        )
        Log.d(TAG, "Email = $emailResult")
        signUpPageUIState.value = signUpPageUIState.value.copy(
            emailError = emailResult.signUpStatus
        )
    }

    private fun validatePhoneNumber(){
        val phoneNumberResult = SignUpPageValidator.validatePhoneNumber(
            phoneNumber = signUpPageUIState.value.phoneNumber
        )
        Log.d(TAG, "Phone Number = $phoneNumberResult")
        signUpPageUIState.value = signUpPageUIState.value.copy(
            phoneNumberError = phoneNumberResult.signUpStatus
        )
    }

    private fun validatePassword(){
        val passwordResult = SignUpPageValidator.validatePassword(
            password = signUpPageUIState.value.password
        )
        Log.d(TAG, "Password = $passwordResult")
        signUpPageUIState.value = signUpPageUIState.value.copy(
            passwordError = passwordResult.signUpStatus
        )
    }

    private fun validateConfirmPassword(){
        val confirmPasswordResult = SignUpPageValidator.validateConfirmPassword(
            confirmPassword = signUpPageUIState.value.confirmPassword
        )
        Log.d(TAG, "Confirm Password = $confirmPasswordResult")
        signUpPageUIState.value = signUpPageUIState.value.copy(
            confirmPasswordError = confirmPasswordResult.signUpStatus
        )
    }

    private fun validateVerificationCode(){
        val validateVerificationCodeResult = SignUpPageValidator.validateVerificationCode(
            verificationCode = signUpPageUIState.value.verificationCode
        )
        Log.d(TAG, "Verification Code = $validateVerificationCodeResult")
        signUpPageUIState.value = signUpPageUIState.value.copy(
            verificationCodeError = validateVerificationCodeResult.signUpStatus
        )
    }

//    private fun validateSignUpDataWithRules(){
//        val firstNameResult = SignUpPageValidator.validateFirstName(
//            firstName = signUpPageUIState.value.firstName
//        )
//
//        val lastNameResult = SignUpPageValidator.validateLastName(
//            lastName = signUpPageUIState.value.lastName
//        )
//
//        val emailResult = SignUpPageValidator.validateEmail(
//            email = signUpPageUIState.value.email
//        )
//
//        val phoneNumberResult = SignUpPageValidator.validatePhoneNumber(
//            phoneNumber = signUpPageUIState.value.phoneNumber
//        )
//
//        val passwordResult = SignUpPageValidator.validatePassword(
//            password = signUpPageUIState.value.password
//        )
//
//        val confirmPasswordResult = SignUpPageValidator.validateConfirmPassword(
//            confirmPassword = signUpPageUIState.value.confirmPassword
//        )
//
//        val verificationResult = SignUpPageValidator.validateVerificationCode(
//            verificationCode = signUpPageUIState.value.verificationCode
//        )
//
//        Log.d(TAG, "FN = ${firstNameResult}")
//        Log.d(TAG, "LN = ${lastNameResult}")
//        Log.d(TAG, "Email = ${emailResult}")
//        Log.d(TAG, "PN = ${phoneNumberResult}")
//        Log.d(TAG, "PW = ${passwordResult}")
//        Log.d(TAG, "ConfPW = ${confirmPasswordResult}")
//        Log.d(TAG, "VerifyCode = ${verificationResult}")
//
//        signUpPageUIState.value = signUpPageUIState.value.copy(
//            firstNameError = firstNameResult.signUpStatus,
//            lastNameError = lastNameResult.signUpStatus,
//            emailError = emailResult.signUpStatus,
//            phoneNumberError = phoneNumberResult.signUpStatus,
//            passwordError = passwordResult.signUpStatus,
//            confirmPasswordError = confirmPasswordResult.signUpStatus,
//            verificationCodeError = verificationResult.signUpStatus
//        )
//    }

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