package com.example.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.loginpage.ui.component.HeadingTextComponent
import com.example.loginpage.ui.component.MyTextFieldComponent
import com.example.loginpage.ui.component.SubButton
import com.example.loginpage.ui.component.TopAppBarBeforeLogin
import com.example.data.viewmodel.HomeViewModel
import com.example.data.uievents.SignUpPageUIEvent
import com.example.data.viewmodel.SignUpPageViewModel
import com.example.loginpage.R

@Composable
fun ForgotPassword(navController: NavHostController,
                   homeViewModel: HomeViewModel, signUpPageViewModel: SignUpPageViewModel
){
    Box(modifier = Modifier.fillMaxSize()){
        ScaffoldWithTopBarForgotPassword(navController,
            homeViewModel, signUpPageViewModel)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldWithTopBarForgotPassword(navController: NavHostController,
                                     homeViewModel: HomeViewModel,
                                     signUpPageViewModel: SignUpPageViewModel){
    val email = stringResource(id = R.string.email)
    val send = stringResource(id = R.string.send)
    val emailPainterResource = painterResource(id = R.drawable.email)

    Scaffold(
        topBar = { TopAppBarBeforeLogin(navController, stringResource(id = R.string.reset_password),
            true, action = "Enter your email to get verified.",
            homeViewModel = homeViewModel) },

        content = {
            Column(
                modifier = Modifier
                    .fillMaxHeight(.8f)
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                HeadingTextComponent(value = "Enter your email")

                Spacer(modifier = Modifier.height(20.dp))

                MyTextFieldComponent(labelValue = email,
                    painterResource = emailPainterResource,
                    onTextChanged = {
                        signUpPageViewModel.onSignUpEvent(
                            SignUpPageUIEvent.EmailChanged(it),
                            navController = navController
                        )
                    },
                    errorStatus = signUpPageViewModel.signUpPageUIState.value.emailError,
                    action = "ForgotPassword"
                )

                Spacer(modifier = Modifier
                    .height(20.dp))

                SubButton(navController = navController,
                    value = send, 2,
                    homeViewModel = homeViewModel,
                    signUpPageViewModel = signUpPageViewModel,
                    isEnable = signUpPageViewModel.emailValidationsPassed.value,
                    originalPage = "ForgotPassword.kt"
                )
            }
        }
    )
}