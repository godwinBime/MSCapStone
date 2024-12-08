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
import com.example.loginpage.ui.component.OtpNotification
import com.example.loginpage.ui.component.SubButton
import com.example.loginpage.ui.component.TopAppBarBeforeLogin
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MFAVerifyEmail(navController: NavHostController,
                   homeViewModel: HomeViewModel = viewModel(),
                   signUpPageViewModel: SignUpPageViewModel = viewModel(),
                   googleSignInViewModel: GoogleSignInViewModel = hiltViewModel()){
    Box(modifier = Modifier.fillMaxSize()){
        ScaffoldMFAVerifyEmail(navController = navController)
        LoadingScreenComponent(googleSignInViewModel = googleSignInViewModel,
            signUpPageViewModel = signUpPageViewModel)
    }
}

private val TAG = SignUpPageViewModel::class.simpleName
//@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScaffoldMFAVerifyEmail(navController: NavHostController,
                           homeViewModel: HomeViewModel = viewModel(),
                           signUpPageViewModel: SignUpPageViewModel = viewModel(),
                           verifyEmailViewModel: VerifyEmailViewModel = viewModel(),
                           timerViewModel: TimerViewModel = viewModel()){
    val verificationCode = stringResource(id = R.string.code)
    val verify = stringResource(id = R.string.verify)
    val codePainterResource = painterResource(id = R.drawable.confirmation_number)
    val user = FirebaseAuth.getInstance()
    val providerId = signUpPageViewModel.checkUserProvider(user = user.currentUser)
    val isEnabled = signUpPageViewModel.verificationCodeValidationsPassed.value
    var codeStatus by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        if (!timerViewModel.isMfaTimerRunning()){
            timerViewModel.mfaStartTimer(timerDuration = 1000)
            Log.d(TAG, "Timer initiated inside ScaffoldMFAVerifyEmail()...${timerViewModel.timeLeft()} seconds left")
        }
    }
    Scaffold(
        topBar = { TopAppBarBeforeLogin(navController, "MFA Email Verify",
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
                LaunchedEffect(Unit) {
                    if (!signUpPageViewModel.isFullNamesObtained(context = context)) {
                        Log.d(TAG, "Inside ScaffoldMFAVerifyEmail() obtaining FullNames...")
                        signUpPageViewModel.fetchedUSerData(
                            signUpPageViewModel = signUpPageViewModel,
                            providerId = providerId,
                            context = context
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                HeadingTextComponent(value = "Enter Verification code")

                Spacer(modifier = Modifier.height(20.dp))
                OtpNotification()
                Spacer(modifier = Modifier.height(20.dp))
                /*if(user.currentUser?.email == "tardzenyuy1989@gmail.com"){
                    Text(
                        text = "Enter this code in the field provided",
                    )
                    verifyEmailViewModel.getOTPCode(context = context)?.let {
                        Text(
                            text = it,
                            color = Color.Red
                        )
                    }
                }*/
                Spacer(modifier = Modifier.height(40.dp))
                MyTextFieldComponent(labelValue = verificationCode,
                    painterResource = codePainterResource,
                    onTextChanged = {
                        signUpPageViewModel.onSignUpEvent(
                            SignUpPageUIEvent.VerificationCodeChanged(it),
                            navController = navController
                        )
                    },
                    errorStatus = signUpPageViewModel.signUpPageUIState.value.verificationCodeError,
                    action = "VerifyAndGotoHomeScreen"
                )
                Spacer(modifier = Modifier.height(50.dp))
                SubButton(navController = navController,
                    value = verify, rank = 5,
                    signUpPageViewModel = signUpPageViewModel,
                    isEnable = isEnabled,
                    originalPage = "MFAVerifyEmail.kt",
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
                Spacer(modifier = Modifier.height(20.dp))
                if(timerViewModel.isTimerRunning()){
                    if (timerViewModel.isMfaTimerRunning()){
                        codeStatus = ""
                    }
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
//                        timerViewModel.mfaResetTimer()
//                        timerViewModel.resetTimer()
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