package com.example.screen

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
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
import com.example.data.viewmodel.ProfileViewModel
import com.example.data.viewmodel.SignUpPageViewModel
import com.example.data.viewmodel.TimerViewModel
import com.example.data.viewmodel.VerifyEmailViewModel
import com.example.loginpage.R
import com.example.loginpage.ui.component.ButtonComponent
import com.example.loginpage.ui.component.ClickableLoginOrLogOutText
import com.example.loginpage.ui.component.DividerTextComponent
import com.example.loginpage.ui.component.GeneralClickableTextComponent
import com.example.loginpage.ui.component.GoogleSignInScreen
import com.example.loginpage.ui.component.HeadingTextComponent
import com.example.loginpage.ui.component.LoadingScreenComponent
import com.example.loginpage.ui.component.MyPasswordFieldComponent
import com.example.loginpage.ui.component.MyTextFieldComponent
import com.example.loginpage.ui.component.NormalTextComponent
import com.example.loginpage.ui.component.ThemeInstructionDialogComponent
import com.example.loginpage.ui.component.TopAppBarBeforeLogin
import com.example.util.isFirstLaunch
import com.example.util.setFirstLaunchFlag
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.log

private val TAG = TimerViewModel::class.simpleName

@Composable
fun Login(navController: NavHostController,
          homeViewModel: HomeViewModel,
          googleSignInViewModel: GoogleSignInViewModel,
          signUpPageViewModel: SignUpPageViewModel = hiltViewModel()){
    val scrollState = rememberScrollState()
    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center){
        ScaffoldLoginWithTopBar(navController = navController,
            homeViewModel = homeViewModel,
            googleSignInViewModel = googleSignInViewModel,
            scrollState = scrollState,
            signUpPageViewModel = signUpPageViewModel,)
        LoadingScreenComponent(googleSignInViewModel = googleSignInViewModel,
            signUpPageViewModel = signUpPageViewModel)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldLoginWithTopBar(navController: NavHostController,
                            timerViewModel: TimerViewModel = viewModel(),
                            homeViewModel: HomeViewModel = viewModel(),
                            googleSignInViewModel: GoogleSignInViewModel = hiltViewModel(),
                            scrollState: ScrollState,
                            profileViewModel: ProfileViewModel = viewModel(),
                            signUpPageViewModel: SignUpPageViewModel = viewModel(),
                            verifyEmailViewModel: VerifyEmailViewModel = viewModel()){
    val context = LocalContext.current
    val showDialog = isFirstLaunch(context = context)
    Log.d(TAG, "Login()...isAuthTimeRecorded: ${timerViewModel.isAuthTimeRecorded(context = context)}")

    LaunchedEffect(Unit) {
        if (timerViewModel.isAuthTimeRecorded(context = context)) {
            timerViewModel.resetAuthStartTime(context = context)
            Log.d(
                TAG,
                "...isAuthTimeRecorded still true: ${timerViewModel.isAuthTimeRecorded(context = context)}"
            )
        }

        if (timerViewModel.isAuthComplete(context = context)) {
            timerViewModel.resetAuthFlag(context = context)
            Log.d(
                TAG,
                "isAuthComplete Login() = ${timerViewModel.isAuthComplete(context = context)}"
            )
        }
    }

    LaunchedEffect(Unit) {
        signUpPageViewModel.resetUserData(context = context)
        profileViewModel.resetProfilePictureFlag(context = context)

    }

    LaunchedEffect(showDialog) {
        if (showDialog) {
            setFirstLaunchFlag(context = context, isFirstLaunch = false)
        }
    }

    if (showDialog) {
        ThemeInstructionDialogComponent(showDialog = showDialog)
    }

    if (timerViewModel.isMfaCounterFinished() || timerViewModel.isTimerFinished()) {
        LaunchedEffect(Unit) {
            verifyEmailViewModel.resetOtpCode()
            timerViewModel.resetTimer()
            timerViewModel.mfaResetTimer()
        }
    }

    Scaffold(
        topBar = { TopAppBarBeforeLogin(
            navController = navController, stringResource(id = R.string.master_title),
            showBackIcon = false, action = stringResource(id = R.string.login_guide),
            homeViewModel = homeViewModel)},
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
                HeadingTextComponent(value = stringResource(id = R.string.sign_in))

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
                    errorStatus = signUpPageViewModel.signUpPageUIState.value.emailError,
                    action = "Login")

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
                    errorStatus = signUpPageViewModel.signUpPageUIState.value.passwordError)
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = signUpPageViewModel.authError.value,
                    color = Color.Red
                )
                Spacer(modifier = Modifier.height(30.dp))

                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                    ButtonComponent(navController,
                        value = stringResource(id = R.string.login),
                        rank = 0,
                        homeViewModel = homeViewModel,
                        onButtonClicked = {
                            signUpPageViewModel.onSignUpEvent(
                                SignUpPageUIEvent.LoginButtonClicked,
                                navController = navController)
                        },
                        isEnable = isEnabled,
                        originalPage = "Login.kt"
                    )
                }

//                Spacer(modifier = Modifier.height(20.dp))
//                GeneralClickableTextComponent(
//                    value = stringResource(id = R.string.code),
//                    navController = navController, 7)

                Spacer(modifier = Modifier.height(10.dp))

                GeneralClickableTextComponent(
                    value = stringResource(id = R.string.forgot_password),
                    navController = navController, 2)

                Spacer(modifier = Modifier.height(10.dp))

                DividerTextComponent(type = stringResource(id = R.string.login))

                Spacer(modifier = Modifier.height(10.dp))

                NormalTextComponent(value = stringResource(id = R.string.sign_in_with))

                Row() {
                    Spacer(modifier = Modifier.width(10.dp))
                    GoogleSignInScreen(googleSignInViewModel = googleSignInViewModel,
                        homeViewModel = homeViewModel,
                        value = stringResource(id = R.string.google),
                        navController = navController)
                    Spacer(modifier = Modifier.width(5.dp))
                }

                Spacer(modifier = Modifier.height(10.dp))

                DividerTextComponent(type = stringResource(id = R.string.login))

                Spacer(modifier = Modifier.height(20.dp))

                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 40.dp),
                    contentAlignment = Alignment.Center) {
                    val initialText = stringResource(id = R.string.not_registered)
                    val loginText = stringResource(id = R.string.create_account)
                    ClickableLoginOrLogOutText(navController = navController,
                        initialText = initialText,
                        loginText = loginText, rank = 1)
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    )
}