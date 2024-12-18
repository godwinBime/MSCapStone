package com.example.screen

import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.data.uievents.SignUpPageUIEvent
import com.example.data.viewmodel.GoogleSignInViewModel
import com.example.data.viewmodel.HomeViewModel
import com.example.data.viewmodel.SignUpPageViewModel
import com.example.data.viewmodel.TimerViewModel
import com.example.data.viewmodel.VerifyEmailViewModel
import com.example.loginpage.R
import com.example.loginpage.ui.component.GeneralClickableTextComponent
import com.example.loginpage.ui.component.HeadingTextComponent
import com.example.loginpage.ui.component.LoadingScreenComponent
import com.example.loginpage.ui.component.MyTextFieldComponent
import com.example.loginpage.ui.component.SubButton
import com.example.loginpage.ui.component.TopAppBarBeforeLogin
import com.google.firebase.auth.FirebaseAuth

@Composable
fun DeleteProfileVerifyEmail(navController: NavHostController,
                             homeViewModel: HomeViewModel,
                             signUpPageViewModel: SignUpPageViewModel,
                             googleSignInViewModel: GoogleSignInViewModel = hiltViewModel()){
    Box(modifier = Modifier.fillMaxSize()){
        ScaffoldDeleteProfileVerifyEmail(navController = navController,
            homeViewModel = homeViewModel,
            signUpPageViewModel = signUpPageViewModel)
        LoadingScreenComponent(googleSignInViewModel = googleSignInViewModel)
    }
}

//@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScaffoldDeleteProfileVerifyEmail(navController: NavHostController,
                                     homeViewModel: HomeViewModel,
                                     signUpPageViewModel: SignUpPageViewModel,
                                     verifyEmailViewModel: VerifyEmailViewModel = viewModel(),
                                     timerViewModel: TimerViewModel = viewModel()){
    val verificationCode = stringResource(id = R.string.code)
    val verify = stringResource(id = R.string.verify)
    val codePainterResource = painterResource(id = R.drawable.confirmation_number)
    val user = FirebaseAuth.getInstance()
    val providerId = signUpPageViewModel.checkUserProvider(user = user.currentUser)
    var codeStatus by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    val TAG = VerifyEmailViewModel::class.simpleName

    LaunchedEffect(Unit) {
        if (!timerViewModel.isMfaTimerRunning()){
            timerViewModel.mfaStartTimer(timerDuration = 1000)
            Log.d(TAG, "Timer initiated inside ScaffoldDeleteProfileVerifyEmail()...${timerViewModel.timeLeft()} seconds left")
        }
    }
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
                    userType = providerId
                )

                Spacer(modifier = Modifier.height(20.dp))
                GeneralClickableTextComponent(
                    value = stringResource(id = R.string.resend_code),
                    navController = navController, rank = 4,
                    type = "ResendOTP")
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = verifyEmailViewModel.errorMessage,
                    color = Color.Red
                )
                Spacer(modifier = Modifier.height(40.dp))
                if(timerViewModel.isTimerRunning()){
//                    if (timerViewModel.isMfaTimerRunning()){
//                        codeStatus = ""
//                    }
                    codeStatus = ""
                    Text(
                        text = stringResource(R.string.request_code) + " " +
                                timerViewModel.timeLeft() + " " + stringResource(R.string.timer_type),
                        color = Color.Red
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                LaunchedEffect(timerViewModel.isMfaTimerRunning()) {
                    if (timerViewModel.isMfaCounterFinished()){
                        verifyEmailViewModel.resetOtpCode()
                        codeStatus = context.getString(R.string.expired_otp)
                    }else{
                        codeStatus = ""
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = codeStatus,
                    color = Color.Red
                )
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    )
}