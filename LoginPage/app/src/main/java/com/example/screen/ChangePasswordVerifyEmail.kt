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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.loginpage.ui.component.GeneralClickableTextComponent
import com.example.loginpage.ui.component.HeadingTextComponent
import com.example.loginpage.ui.component.MyTextFieldComponent
import com.example.loginpage.ui.component.SubButton
import com.example.loginpage.ui.component.TopAppBarBeforeLogin
import com.example.data.viewmodel.HomeViewModel
import com.example.data.uievents.SignUpPageUIEvent
import com.example.data.viewmodel.SignUpPageViewModel
import com.example.data.viewmodel.TimerViewModel
import com.example.loginpage.R
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ChangePasswordVerifyEmail(navController: NavHostController,
                              homeViewModel: HomeViewModel,
                              signUpPageViewModel: SignUpPageViewModel
){
    Box(modifier = Modifier.fillMaxSize()){
        ScaffoldChangePassword(navController,
            homeViewModel, signUpPageViewModel)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldChangePassword(navController: NavHostController,
                           homeViewModel: HomeViewModel = viewModel(),
                           signUpPageViewModel: SignUpPageViewModel = viewModel(),
                           timerViewModel: TimerViewModel = viewModel()){
    val verificationCode = stringResource(id = R.string.code)

    val painterVerificationCode = painterResource(id = R.drawable.confirmation_number)
    val user = FirebaseAuth.getInstance()
    val userType = signUpPageViewModel.checkUserProvider(user = user.currentUser)

    Scaffold(
        topBar = { TopAppBarBeforeLogin(navController, "Change Password Verify Email",
            true, action = "Enter Verification Code sent to your email.",
            homeViewModel = homeViewModel) },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxHeight(.8f)
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Spacer(modifier = Modifier.height(20.dp))

                HeadingTextComponent(value = "Enter Verification Code->")

                Spacer(modifier = Modifier.height(20.dp))
                MyTextFieldComponent(labelValue = verificationCode,
                    painterResource = painterVerificationCode,
                    onTextChanged = {
                        signUpPageViewModel.onSignUpEvent(
                            SignUpPageUIEvent.VerificationCodeChanged(it),
                            navController = navController
                        )
                    },
                    errorStatus = signUpPageViewModel.signUpPageUIState.value.verificationCodeError,
                    action = "ChangePasswordVerifyEmail"
                )

                Spacer(modifier = Modifier
                    .height(50.dp))
                val verify = stringResource(id = R.string.verify)
                SubButton(navController = navController,
                    value = verify,
                    rank = 8,
                    signUpPageViewModel = signUpPageViewModel,
                    isEnable = signUpPageViewModel.verificationCodeValidationsPassed.value,
                    originalPage = "ChangePasswordVerifyEmail.kt",
                    userType = userType
                )

                Spacer(modifier = Modifier.height(20.dp))
                if(timerViewModel.isRunning.value){
                    Text(
                        text = stringResource(R.string.request_code) + " " +
                                timerViewModel.timeLeft.value + " " + stringResource(R.string.timer_type),
                        color = Color.Red
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                GeneralClickableTextComponent(
                    value = stringResource(id = R.string.resend_code),
                    navController = navController, rank = 4,
                    type = "ResendOTP")
            }
        }
    )
}