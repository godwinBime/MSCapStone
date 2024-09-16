package com.example.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import com.example.loginpage.ui.component.GeneralClickableTextComponent
import com.example.loginpage.ui.component.HeadingTextComponent
import com.example.loginpage.ui.component.MyTextFieldComponent
import com.example.loginpage.ui.component.SubButton
import com.example.loginpage.ui.component.TopAppBarBeforeLogin
import com.example.data.viewmodel.HomeViewModel
import com.example.data.uievents.SignUpPageUIEvent
import com.example.data.viewmodel.SignUpPageViewModel
import com.example.loginpage.R

@Composable
fun AuthenticatorAppVerification(navController: NavHostController,
                                 homeViewModel: HomeViewModel,
                                 signUpPageViewModel: SignUpPageViewModel
){
    Box(modifier = Modifier.fillMaxSize()){
        ScaffoldAuthenticatorAppVerification(navController,
            homeViewModel,
            signUpPageViewModel)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldAuthenticatorAppVerification(navController: NavHostController,
                                         homeViewModel: HomeViewModel,
                                         signUpPageViewModel: SignUpPageViewModel
){
    val verificationCode = stringResource(id = R.string.code)
    val verify = stringResource(id = R.string.verify)

    val codePainterResource = painterResource(id = R.drawable.confirmation_number)

    Scaffold(
        topBar = { TopAppBarBeforeLogin(navController, "Auth App Verify",
            true, action = "Goto the Authenticator app and retrieve the code.",
            homeViewModel = homeViewModel) },
        content = {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                HeadingTextComponent(value = "Enter Authenticator app code")

                Spacer(modifier = Modifier
                    .height(20.dp))
                MyTextFieldComponent(labelValue = verificationCode,
                    painterResource = codePainterResource,
                    onTextChanged = {
                        signUpPageViewModel.onSignUpEvent(
                            SignUpPageUIEvent.VerificationCodeChanged(it),
                            navController = navController
                        )
                    },
                    errorStatus = signUpPageViewModel.signUpPageUIState.value.verificationCodeError
                    )

                Spacer(modifier = Modifier.height(50.dp))
                SubButton(navController = navController,
                    value = verify, rank = 4,
                    homeViewModel = homeViewModel,
                    signUpPageViewModel = signUpPageViewModel,
                    isEnable = signUpPageViewModel.verificationCodeValidationsPassed.value
                )

                Spacer(modifier = Modifier.height(20.dp))

                GeneralClickableTextComponent(
                    value = stringResource(id = R.string.resend_code),
                    navController = navController, 7)
            }
        }
    )
}