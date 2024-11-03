package com.example.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
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
import com.example.data.uievents.SignUpPageUIEvent
import com.example.data.viewmodel.HomeViewModel
import com.example.data.viewmodel.SignUpPageViewModel
import com.example.data.viewmodel.TimerViewModel
import com.example.data.viewmodel.VerifyEmailViewModel
import com.example.loginpage.R
import com.example.loginpage.ui.component.GeneralClickableTextComponent
import com.example.loginpage.ui.component.HeadingTextComponent
import com.example.loginpage.ui.component.MyTextFieldComponent
import com.example.loginpage.ui.component.SubButton
import com.example.loginpage.ui.component.TopAppBarBeforeLogin
import com.google.firebase.auth.FirebaseAuth


@Composable
fun DeleteProfileVerifyEmail(navController: NavHostController,
                   homeViewModel: HomeViewModel,
                   signUpPageViewModel: SignUpPageViewModel
){
    Box(modifier = Modifier.fillMaxSize()){
        ScaffoldDeleteProfileVerifyEmail(navController, homeViewModel, signUpPageViewModel)
    }
}

//@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScaffoldDeleteProfileVerifyEmail(navController: NavHostController,
                                     homeViewModel: HomeViewModel,
                                     signUpPageViewModel: SignUpPageViewModel,
                                     emailVerifyEmailViewModel: VerifyEmailViewModel = viewModel(),
                                     timerViewModel: TimerViewModel = viewModel()){
    val verificationCode = stringResource(id = R.string.code)
    val verify = stringResource(id = R.string.verify)

    val codePainterResource = painterResource(id = R.drawable.confirmation_number)
    val user = FirebaseAuth.getInstance()
    val userType = signUpPageViewModel.checkUserProvider(user = user.currentUser)
    Scaffold(
        topBar = { TopAppBarBeforeLogin(navController, "DeleteProfile Email Verify",
            true, action = "Enter Verification code sent to your email.",
            homeViewModel = homeViewModel) },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .padding(paddingValues = paddingValues)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Spacer(modifier = Modifier.height(20.dp))
                HeadingTextComponent(value = "Enter Verification code")

                Spacer(modifier = Modifier.height(20.dp))
                MyTextFieldComponent(labelValue = verificationCode,
                    painterResource = codePainterResource,
                    onTextChanged = {
                        signUpPageViewModel.onSignUpEvent(
                            SignUpPageUIEvent.VerificationCodeChanged(it),
                            navController = navController
                        )
                    },
                    errorStatus = signUpPageViewModel.signUpPageUIState.value.verificationCodeError,
                    action = "DeleteProfile"
                )

                Spacer(modifier = Modifier.height(50.dp))
                SubButton(navController = navController,
                    value = verify, rank = 11,
//                    homeViewModel = homeViewModel,
                    signUpPageViewModel = signUpPageViewModel,
                    isEnable = signUpPageViewModel.verificationCodeValidationsPassed.value,
                    originalPage = "DeleteProfileVerifyEmail.kt",
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
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    )
}