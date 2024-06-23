package com.example.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import com.example.component.ClickableLoginOrLogOutText
import com.example.component.DividerTextComponent
import com.example.component.GeneralClickableTextComponent
import com.example.component.HeadingTextComponent
import com.example.component.MyPasswordFieldComponent
import com.example.component.MyTextFieldComponent
import com.example.component.NormalTextComponent
import com.example.component.TopAppBarBeforeLogin
import com.example.data.signup.SignUpPageUIEvent
import com.example.data.signup.SignUpPageViewModel
import com.example.loginpage.R

@Composable
fun Login(navController: NavHostController, signUpPageViewModel: SignUpPageViewModel){
    val scrollState = rememberScrollState()
    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center){
        ScaffoldLoginWithTopBar(navController = navController, scrollState, signUpPageViewModel)
        if (signUpPageViewModel.signINSignUpInProgress.value) {
            CircularProgressIndicator()
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldLoginWithTopBar(navController: NavHostController,
                            scrollState: ScrollState, signUpPageViewModel: SignUpPageViewModel
){
    Scaffold(
        topBar = { TopAppBarBeforeLogin(navController, stringResource(id = R.string.master_title),
            true, action = "Fill the email and password above to login.") },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(20.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                NormalTextComponent(value = stringResource(id = R.string.welcome))
                HeadingTextComponent(value = "Sign in")

                Spacer(modifier = Modifier.height(20.dp))

                val email = stringResource(id = R.string.email)
                val emailPainterResource = painterResource(id = R.drawable.email)
                val isEnabled = signUpPageViewModel.emailValidationsPassed.value
                        && signUpPageViewModel.passwordValidationsPassed.value

                MyTextFieldComponent(labelValue = email,
                    painterResource = emailPainterResource,
                    onTextChanged = {
                        signUpPageViewModel.onSignUpEvent(
                            SignUpPageUIEvent.EmailChanged(it),
                            navController = navController)
                    },
                    errorStatus = signUpPageViewModel.signUpPageUIState.value.emailError
                )

                Spacer(modifier = Modifier.height(20.dp))
                val password = stringResource(id = R.string.password)
                val passwordPainterResource = painterResource(id = R.drawable.password)

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

                Spacer(modifier = Modifier.height(50.dp))

                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                    ButtonComponent(navController,
                        value = stringResource(id = R.string.login), 0,
                        onButtonClicked = {
                            signUpPageViewModel.onSignUpEvent(
                                SignUpPageUIEvent.LoginButtonClicked,
                                navController = navController)
                        },
                        isEnable = isEnabled
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                GeneralClickableTextComponent(
                    value = stringResource(id = R.string.home),
                    navController = navController, 3)

                Spacer(modifier = Modifier.height(20.dp))

                GeneralClickableTextComponent(
                    value = "Forget Password?",
                    navController = navController, 2)

                Spacer(modifier = Modifier.height(20.dp))

                DividerTextComponent()

                Spacer(modifier = Modifier.height(20.dp))

                NormalTextComponent(value = "Sign in with:")

                DividerTextComponent()

                Spacer(modifier = Modifier.height(20.dp))

                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 40.dp),
                    contentAlignment = Alignment.Center) {
                    val initialText = stringResource(id = R.string.not_registered)
                    val loginText = stringResource(id = R.string.create_account)
                    ClickableLoginOrLogOutText(navController, initialText, loginText, rank = 1)
                }
            }
        }
    )
}