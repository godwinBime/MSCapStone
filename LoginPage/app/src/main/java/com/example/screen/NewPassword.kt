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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.loginpage.ui.component.HeadingTextComponent
import com.example.loginpage.ui.component.MyPasswordFieldComponent
import com.example.loginpage.ui.component.SubButton
import com.example.loginpage.ui.component.TopAppBarBeforeLogin
import com.example.data.viewmodel.HomeViewModel
import com.example.data.uievents.SignUpPageUIEvent
import com.example.data.viewmodel.SignUpPageViewModel
import com.example.data.viewmodel.TimerViewModel
import com.example.data.viewmodel.VerifyEmailViewModel
import com.example.loginpage.R
import com.example.loginpage.ui.component.MyConfirmPasswordFieldComponent
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NewPassword(navController: NavHostController,
                signUpPageViewModel: SignUpPageViewModel,
                homeViewModel: HomeViewModel){
    Box(modifier = Modifier.fillMaxSize()){
        ScaffoldNewPasswordTopBar(navController = navController,
            signUpPageViewModel = signUpPageViewModel,
            homeViewModel = homeViewModel)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldNewPasswordTopBar(navController: NavHostController,
                              signUpPageViewModel: SignUpPageViewModel = viewModel(),
                              timerViewModel: TimerViewModel = viewModel(),
                              verifyEmailViewModel: VerifyEmailViewModel = viewModel(),
                              homeViewModel: HomeViewModel = viewModel()){
    val oldPassword = stringResource(id = R.string.old_password)
    val newPassword = stringResource(id = R.string.new_password)
    val resetPassword = stringResource(id = R.string.reset_password)
    val oldPasswordPainterResource = painterResource(id = R.drawable.password)
    val newPasswordPainterResource = painterResource(id = R.drawable.password)
    val user = FirebaseAuth.getInstance()
    val userType = signUpPageViewModel.checkUserProvider(user = user.currentUser)

    if (timerViewModel.isTimerFinished() || timerViewModel.isMfaCounterFinished()){
        LaunchedEffect(Unit) {
            verifyEmailViewModel.resetOtpCode()
            timerViewModel.resetTimer()
            timerViewModel.mfaResetTimer()
        }
    }

    Scaffold(
        topBar = { TopAppBarBeforeLogin(navController, "New Password",
            true, action = stringResource(R.string.enter_new_password),
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
                HeadingTextComponent(value = stringResource(R.string.enter_new_password))

                Spacer(modifier = Modifier.height(20.dp))
                MyPasswordFieldComponent(labelValue = oldPassword,
                    painterResource = oldPasswordPainterResource,
                    onTextChanged = {
                        signUpPageViewModel.onSignUpEvent(
                            SignUpPageUIEvent.PasswordChanged(it),
                            navController = navController
                        )
                    },
                    errorStatus = signUpPageViewModel.signUpPageUIState.value.passwordError
                    )

                Spacer(modifier = Modifier.height(20.dp))
                MyConfirmPasswordFieldComponent(labelValue = newPassword,
                    painterResource = newPasswordPainterResource,
                    onTextChanged = {
                        signUpPageViewModel.onSignUpEvent(
                            SignUpPageUIEvent.ConfirmPasswordChanged(it),
                            navController = navController
                        )
                    },
                    errorStatus = signUpPageViewModel.signUpPageUIState.value.confirmPasswordError
                )
                Spacer(modifier = Modifier.height(60.dp))

                val isEnabled = signUpPageViewModel.passwordValidationsPassed.value && signUpPageViewModel.confirmPasswordValidationsPassed.value

                SubButton(
                    navController = navController,
                    value = resetPassword,
                    rank = 7,
//                    homeViewModel = homeViewModel,
                    signUpPageViewModel = signUpPageViewModel,
                    isEnable = isEnabled,
//                    isEnable = signUpPageViewModel.passwordValidationsPassed.value,
                    originalPage = "NewPassword.kt",
                    userType = userType
                /*&& signUpPageViewModel.confirmPasswordValidationsPassed.value*/
                )
            }
        }
    )
}