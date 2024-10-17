package com.example.data.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.data.rules.SignUpPageValidator
import com.example.data.uievents.SignUpPageUIEvent
import com.example.data.uistate.SignUpPageUIState
import com.example.data.uistate.UserData
import com.example.navigation.Routes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class SignUpPageViewModel: ViewModel() {
    private val TAG = SignUpPageViewModel::class.simpleName
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    var signUpPageUIState = mutableStateOf(SignUpPageUIState())
    var firstNameValidationsPassed = mutableStateOf(false)
    var lastNameValidationsPassed = mutableStateOf(false)
    var emailValidationsPassed = mutableStateOf(false)
    var phoneNumberValidationsPassed = mutableStateOf(false)
    var passwordValidationsPassed = mutableStateOf(false)
    var confirmPasswordValidationsPassed = mutableStateOf(false)
    var verificationCodeValidationsPassed = mutableStateOf(false)
    var privacyPolicyValidationPassed = mutableStateOf(false)
    var signInSignUpInProgress = mutableStateOf(false)

    var fullNames = ""
    var phoneNumber = ""
    var userEmail = ""

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
        signInSignUpInProgress.value = true
        val email = signUpPageUIState.value.email
        val password = signUpPageUIState.value.password

        FirebaseAuth
            .getInstance()
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                Log.d(TAG, "Inside login addOnCompleteListener... by ${signUpPageUIState.value.email}")
                Log.d(TAG, "Is Login Success: ${it.isSuccessful}")
                if (it.isSuccessful){
                    checkUserProvider(auth.currentUser)
                    Log.d(TAG, "Login-ID: ${auth.currentUser?.uid}")
                    Log.d(TAG, "Going to choose verification method with email: ${signUpPageUIState.value.email}")
                    navController.navigate(Routes.ChooseVerificationMethod.route)
                    signInSignUpInProgress.value = false
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "Inside Firebase Login addOnFailureListener")
                Log.d(TAG, "Login Exception = ${it.message}")
                Log.d(TAG, "Login Exception = ${it.localizedMessage}")
                signInSignUpInProgress.value = false
            }
    }

    private fun signUp(navController: NavHostController){
        Log.d(TAG, "SignUp Button Clicked...")
        printSignUpState("Auth to create user account in Firebase DB...")
        signInSignUpInProgress.value = true
        createUserInFireBase(
            email = signUpPageUIState.value.email,
            password = signUpPageUIState.value.password,
            navController = navController)
    }

    //Create authentication details in firebase database
    private fun createUserInFireBase(email: String,
                                     password: String,
                                     navController: NavHostController){
        if (email.isNotEmpty() && password.isNotEmpty()) {
            FirebaseAuth
                .getInstance()
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    Log.d(TAG, "Inside Firebase SignUp addOnCompleteListener")
                    Log.d(TAG, "SignUP isSuccessful: ${it.isSuccessful}")
                    if (it.isSuccessful) {
                        val userId = auth.currentUser?.uid
                        storeUserData(
                            userId = userId,
                            signUpPageUIState.value.firstName,
                            signUpPageUIState.value.lastName,
                            signUpPageUIState.value.phoneNumber,
                            signUpPageUIState.value.email,
                            navController = navController
                        )
                        signInSignUpInProgress.value = false
                    }
                }
                .addOnFailureListener {
                    signInSignUpInProgress.value = false
                    Log.d(TAG, "Inside Firebase Signup addOnFailureListener")
                    Log.d(TAG, "SignUp Exception = ${it.message}")
                    Log.d(TAG, "SignUp Exception = ${it.localizedMessage}")
                }
        }else{
            Log.d(TAG, "SignUP UnSuccessful no email or password provided")
        }
    }

    private fun storeUserData(
        userId: String?, firstName: String,
        lastName: String, phoneNumber: String,
        email: String,
        navController: NavHostController
    ){
        val userType = checkUserProvider(auth.currentUser)
        val userData = UserData(
            userId = userId,
            firstName = firstName,
            lastName = lastName,
            phoneNumber = phoneNumber,
            email = email)
        if (userType == "password") {
            try {
                firestore.collection("userdata").add(userData)
                    .addOnSuccessListener {
                        Log.d(TAG, "New User-ID: ${auth.currentUser?.uid}")
                        Log.d(TAG, "New user firstName: $firstName")
                        Log.d(TAG, "New user lastName: $lastName")
                        Log.d(TAG, "New user phoneNumber: $phoneNumber")
                        navController.navigate(Routes.Login.route)
                    }
                    .addOnFailureListener {
                        Log.d(TAG, "storeUserData Error: ${it.message}")
                    }
            } catch (e: Exception) {
                Log.d(TAG, "addStoreUserData Exception: ${e.message}")
            } finally {
                signInSignUpInProgress.value = false
            }
        }else if (userType == "google.com"){
            Log.d(TAG, "storeUserData Error: Login through 3rd party (google.com) credentials. \nNo data provided.")
        }else{
            Log.d(TAG, "storeUserData Error: No user logged in yet...")
        }
    }

    //  Fetch user data from firebase database
    fun fetchUserData(signUpPageViewModel: SignUpPageViewModel,
                              userId: String?, onUserDataFetched: (UserData) -> Unit){
        val userType = signUpPageViewModel.checkUserProvider(auth.currentUser)
        if (auth.currentUser != null && userType == "password"){
            signInSignUpInProgress.value = true
            try {
                val query = firestore.collection("userdata").whereEqualTo("userId", userId)
                query.get()
                    .addOnSuccessListener { querySnapshot ->
                        if (!querySnapshot.isEmpty){
                            val documentSnapshot = querySnapshot.documents[0]
                            val user = documentSnapshot.toObject(UserData::class.java)
                            if (user != null){
                                onUserDataFetched(user)
                            }else{
                                Log.d(TAG, "fetchUserData Error: User with ID: $userId not found ")
                            }
                        }else{
                            Log.d(TAG, "fetchUserData Error: No matching document found")
                        }
                    }
            }catch (e: Exception){
                Log.d(TAG, "fetchUserData Exception: ${e.message} ")
            }finally {
                signInSignUpInProgress.value = false
            }
        }else if (userType == "google.com"){
            Log.d(TAG, "fetchUserData Error: \nLogin through 3rd party (google.com) credentials. \nNo data provided.")
        }else{
            Log.d(TAG, "fetchUserData Error: No user logged in yet...")
        }
    }

    fun fetchedUSerData(signUpPageViewModel: SignUpPageViewModel){
        val userId = auth.currentUser?.uid
        val userType = checkUserProvider(user = auth.currentUser)
//        Log.d(TAG,"Full Names: ${auth.currentUser?.displayName}")
//        Log.d(TAG,"Cell: ${auth.currentUser?.phoneNumber}")
        if (userType == "password") {
            fetchUserData(signUpPageViewModel = signUpPageViewModel, userId = userId) { user ->
                fullNames = user.firstName + " " + user.lastName
                phoneNumber = user.phoneNumber
                userEmail = user.email
                Log.d(TAG, "FirstName: ${user.firstName}")
                Log.d(TAG, "LastName: ${user.lastName}")
                Log.d(TAG, "Cell: ${user.phoneNumber}")
            }
        }else if(userType == "google.com"){
            Log.d(TAG, "fetchedUserData Error: \nLogin through 3rd party (google.com) credentials. \nNo data provided.")
        }
    }

    fun checkUserProvider(user: FirebaseUser?): String{
        user?.let {
            val providerData = it.providerData
            for (profile in providerData){
                val providerId = profile.providerId
                when(providerId){
                    "password" -> {
                        Log.d(TAG, "ProviderId call inside checkUserProvider(): email/password")
                        return "password"
                    }
                    "google.com" -> {
                        Log.d(TAG, "ProviderId call inside checkUserProvider(): google.com")
                        return "google.com"
                    }
                }
            }
        }
        return "None"
    }
}