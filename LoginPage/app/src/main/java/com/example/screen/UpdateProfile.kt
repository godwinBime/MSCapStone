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
import com.example.component.CustomTopAppBar
import com.example.component.HeadingTextComponent
import com.example.component.MyPasswordFieldComponent
import com.example.component.MyTextFieldComponent
import com.example.data.viewmodel.HomeViewModel
import com.example.data.uievents.SignUpPageUIEvent
import com.example.data.viewmodel.SignUpPageViewModel
import com.example.loginpage.R

@Composable
fun UpdateProfile(navController: NavHostController, signUpPageViewModel: SignUpPageViewModel,
                  homeViewModel: HomeViewModel
){
    val scrollState = rememberScrollState()
    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center){
        ScaffoldUpdateProfileWithTopBar(navController, scrollState, signUpPageViewModel = signUpPageViewModel,
            homeViewModel = homeViewModel)
        if (signUpPageViewModel.signINSignUpInProgress.value) {
            CircularProgressIndicator()
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldUpdateProfileWithTopBar(navController: NavHostController, scrollState: ScrollState,
                                    signUpPageViewModel: SignUpPageViewModel, homeViewModel: HomeViewModel
){
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

    val updateProfileTitle = stringResource(id = R.string.update_profile)
    val updateButton = stringResource(id = R.string.update_button)

    Scaffold(
        topBar = { CustomTopAppBar(navController, updateProfileTitle, true,
            logoutButtonClicked = {
                homeViewModel.logOut(navController = navController)
            }
        )},
        content = {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                HeadingTextComponent(value = updateProfileTitle)

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

                Spacer(modifier = Modifier.height(60.dp))
                Box(modifier = Modifier
                    .padding(55.dp, 0.dp, 55.dp, 0.dp)){
                    ButtonComponent(navController,
                        value = updateButton, 4,
                        homeViewModel = homeViewModel,
                        onButtonClicked = {
                            signUpPageViewModel.onSignUpEvent(
                                SignUpPageUIEvent.RegisterButtonClicked,
                                navController = navController
                            )
                        },
                        isEnable = isEnabled
                    )
                }
            }
        }
    )
}