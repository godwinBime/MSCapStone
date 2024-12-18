package com.example.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.loginpage.ui.component.ButtonComponent
import com.example.loginpage.ui.component.CheckBoxComponent
import com.example.loginpage.ui.component.ClickableLoginOrLogOutText
import com.example.loginpage.ui.component.DividerTextComponent
import com.example.loginpage.ui.component.HeadingTextComponent
import com.example.loginpage.ui.component.MyPasswordFieldComponent
import com.example.loginpage.ui.component.MyTextFieldComponent
import com.example.loginpage.ui.component.TopAppBarBeforeLogin
import com.example.data.viewmodel.HomeViewModel
import com.example.data.uievents.SignUpPageUIEvent
import com.example.data.viewmodel.GoogleSignInViewModel
import com.example.data.viewmodel.SignUpPageViewModel
import com.example.loginpage.R
import com.example.loginpage.ui.component.LoadingScreenComponent

@Composable
fun SignUp(navController: NavHostController,
           homeViewModel: HomeViewModel,
           googleSignInViewModel: GoogleSignInViewModel = hiltViewModel(),
           signUpPageViewModel: SignUpPageViewModel
){
    val scrollState = rememberScrollState()
    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center){
        ScaffoldSignUpWithTopBar(navController,
            homeViewModel,
            scrollState, signUpPageViewModel = signUpPageViewModel)
        LoadingScreenComponent(googleSignInViewModel = googleSignInViewModel,
            signUpPageViewModel = signUpPageViewModel)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldSignUpWithTopBar(navController: NavHostController, homeViewModel: HomeViewModel,
                             scrollState: ScrollState,
                             signUpPageViewModel: SignUpPageViewModel){
    val firstName = stringResource(id = R.string.first_name)
    val lastName = stringResource(id = R.string.last_name)
    val email = stringResource(id = R.string.email)
    val phoneNumber = stringResource(id = R.string.phone_number)
    val password = stringResource(id = R.string.password)
    val emailPainterResource = painterResource(id = R.drawable.email)
    val personPainterResource = painterResource(id = R.drawable.person)
    val phoneNumberPainterResource = painterResource(id = R.drawable.phone)
    val passwordPainterResource = painterResource(id = R.drawable.password)

    val isEnabled = signUpPageViewModel.firstNameValidationsPassed.value &&
            signUpPageViewModel.lastNameValidationsPassed.value &&
            signUpPageViewModel.emailValidationsPassed.value &&
            signUpPageViewModel.phoneNumberValidationsPassed.value &&
            signUpPageViewModel.passwordValidationsPassed.value &&
            signUpPageViewModel.privacyPolicyValidationPassed.value

    val createAccount = stringResource(id = R.string.create_account)
    Scaffold(
        topBar = { TopAppBarBeforeLogin(navController, createAccount,
            true, action = "Fill the fields above to get registered.",
            homeViewModel)
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                HeadingTextComponent(value = "Create an Account")

                Spacer(modifier = Modifier
                    .height(20.dp))
                MyTextFieldComponent(labelValue = firstName,
                    painterResource = personPainterResource,
                    onTextChanged = {
                        signUpPageViewModel.onSignUpEvent(
                            SignUpPageUIEvent.FirstNameChanged(it),
                            navController = navController)
                    },
                    errorStatus = signUpPageViewModel.signUpPageUIState.value.firstNameError,
                    action = "SignUp")

                Spacer(modifier = Modifier
                     .height(20.dp))
                MyTextFieldComponent(labelValue = lastName,
                    painterResource = personPainterResource,
                    onTextChanged = {
                        signUpPageViewModel.onSignUpEvent(
                            SignUpPageUIEvent.LastNameChanged(it),
                            navController = navController
                        )
                    },
                    errorStatus = signUpPageViewModel.signUpPageUIState.value.lastNameError)

                Spacer(modifier = Modifier.height(20.dp))

                MyTextFieldComponent(labelValue = email,
                    painterResource = emailPainterResource,
                    onTextChanged = {
                        signUpPageViewModel.onSignUpEvent(
                            SignUpPageUIEvent.EmailChanged(it),
                            navController = navController
                        )
                    },
                    errorStatus = signUpPageViewModel.signUpPageUIState.value.emailError
                )

                Spacer(modifier = Modifier.height(20.dp))
                MyPasswordFieldComponent(labelValue = password,
                    painterResource = passwordPainterResource,
                    onTextChanged = {
                        signUpPageViewModel.onSignUpEvent(
                            SignUpPageUIEvent.PasswordChanged(it),
                            navController = navController
                        )
                    },
                    errorStatus = signUpPageViewModel.signUpPageUIState.value.passwordError
                )

                /*
                Spacer(modifier = Modifier.height(20.dp))
                MyConfirmPasswordFieldComponent(labelValue = confirmPassword,
                    painterResource = confirmPasswordPainterResource,
                    onTextChanged = {
                        signUpPageViewModel.onSignUpEvent(
                            SignUpPageUIEvent.ConfirmPasswordChanged(it),
                            navController = navController
                        )
                    },
                    errorStatus = signUpPageViewModel.signUpPageUIState.value.confirmPasswordError
                )
                */

                Spacer(modifier = Modifier.height(20.dp))
                MyTextFieldComponent(labelValue = phoneNumber,
                    painterResource = phoneNumberPainterResource,
                    onTextChanged = {
                        signUpPageViewModel.onSignUpEvent(
                            SignUpPageUIEvent.PhoneNumberChanged(it),
                            navController = navController
                        )
                    },
                    errorStatus = signUpPageViewModel.signUpPageUIState.value.phoneNumberError,
                    action = "SignUpPhoneNumber")

                CheckBoxComponent(value = "SignUp", navController = navController,
                    onCheckBoxChecked = {
                        signUpPageViewModel.onSignUpEvent(
                            SignUpPageUIEvent.PrivacyPolicyCheckboxClicked(it),
                            navController = navController
                        )
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = signUpPageViewModel.authError.value,
                    color = Color.Red
                )
                Spacer(modifier = Modifier.height(20.dp))
                Box(modifier = Modifier
                    .padding(55.dp, 0.dp, 55.dp, 0.dp)){
                    ButtonComponent(navController,
                        value = stringResource(id = R.string.signup),
                        rank = 1,
                        homeViewModel = homeViewModel,
                        onButtonClicked = {
                            signUpPageViewModel.onSignUpEvent(
                                SignUpPageUIEvent.RegisterButtonClicked,
                                navController = navController
                            )
                        },
                        isEnable = isEnabled,
                        originalPage = "SignUp.kt"
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                DividerTextComponent()

                Spacer(modifier = Modifier.height(20.dp))

                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 40.dp),
                    contentAlignment = Alignment.Center) {
                    val initialText = stringResource(id = R.string.already_have_account)
                    val loginText = stringResource(id = R.string.login)
                    ClickableLoginOrLogOutText(navController, initialText, loginText, rank = 0)
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    )
}