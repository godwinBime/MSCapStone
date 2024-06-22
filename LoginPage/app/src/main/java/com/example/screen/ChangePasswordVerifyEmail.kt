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
import com.example.component.CustomTopAppBar
import com.example.component.HeadingTextComponent
import com.example.component.MyTextFieldComponent
import com.example.component.SubButton
import com.example.data.SignUpPageUIEvent
import com.example.data.SignUpPageViewModel
import com.example.loginpage.R

@Composable
fun ChangePasswordVerifyEmail(navController: NavHostController, signUpPageViewModel: SignUpPageViewModel){
    Box(modifier = Modifier.fillMaxSize()){
        ScaffoldChangePassword(navController, signUpPageViewModel)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldChangePassword(navController: NavHostController, signUpPageViewModel: SignUpPageViewModel){
    val verificationCode = stringResource(id = R.string.code)

    val painterVerificationCode = painterResource(id = R.drawable.confirmation_number)

    Scaffold(
//        topBar = { CustomTopAppBar(navController, "Verify Email", true)},
        content = {
            Column(
                modifier = Modifier
                    .fillMaxHeight(.8f)
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Spacer(modifier = Modifier.height(20.dp))

                HeadingTextComponent(value = "Enter Verification Code")

                Spacer(modifier = Modifier.height(20.dp))
                MyTextFieldComponent(labelValue = verificationCode,
                    painterResource = painterVerificationCode,
                    onTextChanged = {
                        signUpPageViewModel.onSignUpEvent(
                            SignUpPageUIEvent.VerificationCodeChanged(it),
                            navController = navController
                        )
                    },
                    errorStatus = signUpPageViewModel.signUpPageUIState.value.verificationCodeError
                )

                Spacer(modifier = Modifier
                    .height(20.dp))
                val verify = stringResource(id = R.string.verify)
                SubButton(navController = navController,
                    value = verify,  rank = 3,
                    signUpPageViewModel = signUpPageViewModel,
                    isEnable = signUpPageViewModel.verificationCodeValidationsPassed.value
                    )
            }
        }
    )
}