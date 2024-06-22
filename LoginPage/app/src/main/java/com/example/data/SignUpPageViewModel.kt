package com.example.data

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.data.rules.SignUpPageValidator
import com.example.navigation.Routes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener

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
    var privacyPolicyValidationPassed = mutableStateOf(false)
    var signINSignUpInProgress = mutableStateOf(false)

    fun onSignUpEvent(signUpEvent: SignUpPageUIEvent, navController: NavHostController){
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
                Log.d(TAG, "Password String Inside Password UIEvent = ${signUpEvent.password}")
                printSignUpState("Password")
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
                signUp(navController = navController)
            }

            is SignUpPageUIEvent.RegisterButtonClickedAfterFirebaseAuth -> {
                navigateAfterLogin()
            }

            is SignUpPageUIEvent.LoginButtonClicked -> {
                logUserIn(navController = navController)
            }

            is SignUpPageUIEvent.PrivacyPolicyCheckboxClicked -> {
                validatePrivacyPolicyCodeDataWithRules()
                signUpPageUIState.value = signUpPageUIState.value.copy(
                    privacyPolicyAccepted = signUpEvent.privacyPolicyStatus
                )
                printSignUpState("Privacy_Policy")
            }
        }
    }

    private fun validateFirstNameDataWithRules(){
        val firstNameResult = SignUpPageValidator.validateFirstName(
            firstName = signUpPageUIState.value.firstName
        )
        Log.d(TAG, "FN = $firstNameResult")
        signUpPageUIState.value = signUpPageUIState.value.copy(
            firstNameError = firstNameResult.signUpStatus
        )
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
        Log.d(TAG, "Password.. = $passwordResult")
        signUpPageUIState.value = signUpPageUIState.value.copy(
            passwordError = passwordResult.signUpStatus
        )
        passwordValidationsPassed.value = passwordResult.signUpStatus
    }

    private fun validateConfirmPasswordDataWithRules(){
        val confirmPasswordResult = SignUpPageValidator.validateConfirmPassword(
            confirmPassword = signUpPageUIState.value.confirmPassword,
            password = signUpPageUIState.value.password
        )
        signUpPageUIState.value = signUpPageUIState.value.copy(
            confirmPasswordError = confirmPasswordResult.signUpStatus
        )
        confirmPasswordValidationsPassed.value = confirmPasswordResult.signUpStatus
        Log.d(TAG, "Confirm Password... = $confirmPasswordResult")
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

    private fun validatePrivacyPolicyCodeDataWithRules(){
        val privacyPolicyResult = SignUpPageValidator.validatePrivacyPolicyAcceptanceState(
            privacyPolicyStatusValue = signUpPageUIState.value.privacyPolicyAccepted
        )
        Log.d(TAG, "Privacy policy state: $privacyPolicyResult")
        signUpPageUIState.value = signUpPageUIState.value.copy(
            privacyPolicyError = privacyPolicyResult.signUpStatus
        )
        privacyPolicyValidationPassed.value = privacyPolicyResult.signUpStatus
    }

    private fun printSignUpState(value: String){
        Log.d(TAG, "Inside_${value}_printState...")
//        Log.d(TAG, "_printState $value: ${signUpPageUIState.value}")
    }

    private fun signUp(navController: NavHostController){
        Log.d(TAG, "SignUp Button Clicked...")
        printSignUpState("Auth to Firebase DB...")
        createUserInFireBase(
            email = signUpPageUIState.value.email,
            password = signUpPageUIState.value.password,
            navController = navController)
    }

    private fun navigateAfterLogin(){
        Log.d(TAG, "NavigateAfterSignUp Button Clicked...")
        printSignUpState("NavigateAfterSignUp to Firebase DB...")
    }

    private fun logUserIn(navController: NavHostController){
        Log.d(TAG, "Login Button Clicked...")
        printSignUpState("Logging into Firebase DB...")
        login(navController = navController)
    }

    private fun login(navController: NavHostController){
        signINSignUpInProgress.value = true
        val email = signUpPageUIState.value.email
        val password = signUpPageUIState.value.password

        FirebaseAuth
            .getInstance()
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                Log.d(TAG, "Inside login success...")
                Log.d(TAG, "Is Login Success: ${it.isSuccessful}")

                if (it.isSuccessful){
                    navController.navigate(Routes.ChooseVerificationMethod.route)
                    signINSignUpInProgress.value = false
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "Inside Firebase Login addOnFailureListener")
                Log.d(TAG, "Login Exception = ${it.message}")
                Log.d(TAG, "Login Exception = ${it.localizedMessage}")
                signINSignUpInProgress.value = false
            }
    }

    //Create authentication details in firebase database
    private fun createUserInFireBase(email: String, password: String, navController: NavHostController){
        signINSignUpInProgress.value = true
        FirebaseAuth
            .getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                Log.d(TAG, "Inside Firebase SignUp addOnCompleteListener")
                Log.d(TAG, "SignUP isSuccessful: ${it.isSuccessful}")
                if (it.isSuccessful){
                    navController.navigate(Routes.Login.route)
                    signINSignUpInProgress.value = false
                }
            }
            .addOnFailureListener {
                signINSignUpInProgress.value = false
                Log.d(TAG, "Inside Firebase Signup addOnFailureListener")
                Log.d(TAG, "SignUp Exception = ${it.message}")
                Log.d(TAG, "SignUp Exception = ${it.localizedMessage}")
            }
    }

    fun logOut(navController: NavHostController){
        val firebaseAuth = FirebaseAuth
            .getInstance()
        firebaseAuth.signOut()

        val authStateListener = AuthStateListener{
            if (it.currentUser == null){
                navController.navigate(Routes.Login.route)
                Log.d(TAG, "Inside sign out success state")
            }else{
                Log.d(TAG, "Inside sign out is not complete state...")
            }
        }
        firebaseAuth.addAuthStateListener(authStateListener)
    }
}