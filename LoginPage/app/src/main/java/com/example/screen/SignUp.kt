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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.component.ButtonComponent
import com.example.component.CheckBoxComponent
import com.example.component.ClickableLoginOrLogOutText
import com.example.component.CustomTopAppBar
import com.example.component.DividerTextComponent
import com.example.component.HeadingTextComponent
import com.example.component.MyConfirmPasswordFieldComponent
import com.example.component.MyPasswordFieldComponent
import com.example.component.MyTextFieldComponent
import com.example.data.SignUpPageUIEvent
import com.example.data.SignUpPageViewModel
import com.example.loginpage.R

@Composable
fun SignUp(navController: NavHostController, signUpPageViewModel: SignUpPageViewModel){
    val scrollState = rememberScrollState()
    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center){
        ScaffoldSignUpWithTopBar(navController, scrollState, signUpPageViewModel = signUpPageViewModel)
        if (signUpPageViewModel.signINSignUpInProgress.value) {
            CircularProgressIndicator()
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldSignUpWithTopBar(navController: NavHostController, scrollState: ScrollState,
                             signUpPageViewModel: SignUpPageViewModel){
    val firstName = stringResource(id = R.string.first_name)
    val lastName = stringResource(id = R.string.last_name)
    val email = stringResource(id = R.string.email)
    val phoneNumber = stringResource(id = R.string.phone_number)
    val password = stringResource(id = R.string.password)
    val confirmPassword = stringResource(id = R.string.confirm_password)

    val emailPainterResource = painterResource(id = R.drawable.email)
    val personPainterResource = painterResource(id = R.drawable.person)
    val phoneNumberPainterResource = painterResource(id = R.drawable.phone)
    val passwordPainterResource = painterResource(id = R.drawable.password)
    val confirmPasswordPainterResource = painterResource(id = R.drawable.password)

    val isEnabled = signUpPageViewModel.firstNameValidationsPassed.value &&
            signUpPageViewModel.lastNameValidationsPassed.value &&
            signUpPageViewModel.emailValidationsPassed.value &&
            signUpPageViewModel.phoneNumberValidationsPassed.value &&
            signUpPageViewModel.passwordValidationsPassed.value &&
            //signUpPageViewModel.confirmPasswordValidationsPassed.value &&
            signUpPageViewModel.privacyPolicyValidationPassed.value

    val createAccount = stringResource(id = R.string.create_account)
    Scaffold(
//        topBar = { CustomTopAppBar(navController, createAccount, true)},
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
                    errorStatus = signUpPageViewModel.signUpPageUIState.value.firstNameError)

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

//                Spacer(modifier = Modifier.height(20.dp))
//                MyConfirmPasswordFieldComponent(labelValue = confirmPassword,
//                    painterResource = confirmPasswordPainterResource,
//                    onTextChanged = {
//                        signUpPageViewModel.onSignUpEvent(
//                            SignUpPageUIEvent.ConfirmPasswordChanged(it),
//                            navController = navController
//                        )
//                    },
//                    errorStatus = signUpPageViewModel.signUpPageUIState.value.confirmPasswordError
//                )

                Spacer(modifier = Modifier.height(20.dp))
                MyTextFieldComponent(labelValue = phoneNumber,
                    painterResource = phoneNumberPainterResource,
                    onTextChanged = {
                        signUpPageViewModel.onSignUpEvent(
                            SignUpPageUIEvent.PhoneNumberChanged(it),
                            navController = navController
                        )
                    },
                    errorStatus = signUpPageViewModel.signUpPageUIState.value.phoneNumberError
                )

                CheckBoxComponent(value = "SignUp", navController = navController,
                    onCheckBoxChecked = {
                        signUpPageViewModel.onSignUpEvent(
                            SignUpPageUIEvent.PrivacyPolicyCheckboxClicked(it),
                            navController = navController
                        )
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))
                Box(modifier = Modifier
                    .padding(55.dp, 0.dp, 55.dp, 0.dp)){
                    ButtonComponent(navController,
                        value = stringResource(id = R.string.signup), 1,
                        onButtonClicked = {
                            signUpPageViewModel.onSignUpEvent(
                                SignUpPageUIEvent.RegisterButtonClicked,
                                navController = navController
                            )
                        },
                        isEnable = isEnabled
                    )
                }

                DividerTextComponent()

                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 40.dp),
                    contentAlignment = Alignment.Center) {
                    val initialText = stringResource(id = R.string.already_have_account)
                    val loginText = stringResource(id = R.string.login)
                    ClickableLoginOrLogOutText(navController, initialText, loginText, rank = 0)
                }
            }
        }
    )
}