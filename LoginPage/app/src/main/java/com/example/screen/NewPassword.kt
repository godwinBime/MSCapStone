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
import com.example.component.HeadingTextComponent
import com.example.component.MyPasswordFieldComponent
import com.example.component.SubButton
import com.example.component.TopAppBarBeforeLogin
import com.example.data.viewmodel.HomeViewModel
import com.example.data.uievents.SignUpPageUIEvent
import com.example.data.viewmodel.SignUpPageViewModel
import com.example.loginpage.R

@Composable
fun NewPassword(navController: NavHostController, signUpPageViewModel: SignUpPageViewModel,
                homeViewModel: HomeViewModel
){
    Box(modifier = Modifier.fillMaxSize()){
        ScaffoldNewPasswordTopBar(navController, signUpPageViewModel, homeViewModel)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldNewPasswordTopBar(navController: NavHostController,
                              signUpPageViewModel: SignUpPageViewModel,
                              homeViewModel: HomeViewModel
){
    val password = stringResource(id = R.string.password)
    val confirmPassword = stringResource(id = R.string.confirm_password)
    val resetPassword = stringResource(id = R.string.reset_password)

    val passwordPainterResource = painterResource(id = R.drawable.password)


    Scaffold(
        topBar = { TopAppBarBeforeLogin(navController, "New Password",
            true, action = "Enter new password above.",
            homeViewModel = homeViewModel) },
        content = {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxHeight(.8f)
                    ,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Spacer(modifier = Modifier.height(20.dp))
                HeadingTextComponent(value = "Enter new password")

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

                Spacer(modifier = Modifier.height(20.dp))
//                MyConfirmPasswordFieldComponent(labelValue = confirmPassword,
//                    painterResource = passwordPainterResource,
//                    onTextChanged = {
//                        signUpPageViewModel.onSignUpEvent(
//                            SignUpPageUIEvent.ConfirmPasswordChanged(it),
//                            navController = navController)
//                    },
//                    errorStatus = signUpPageViewModel.signUpPageUIState.value.confirmPasswordError
//                )
                Spacer(modifier = Modifier.height(20.dp))

                SubButton(
                    navController = navController,
                    value = resetPassword,
                    rank = 4,
                    homeViewModel = homeViewModel,
                    signUpPageViewModel = signUpPageViewModel,
                    isEnable = signUpPageViewModel.passwordValidationsPassed.value
                /*&& signUpPageViewModel.confirmPasswordValidationsPassed.value*/
                )
            }
        }
    )
}