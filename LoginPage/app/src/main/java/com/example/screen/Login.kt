package com.example.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
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
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.data.uievents.SignUpPageUIEvent
import com.example.data.viewmodel.GoogleSignInViewModel
import com.example.data.viewmodel.HomeViewModel
import com.example.data.viewmodel.SignUpPageViewModel
import com.example.loginpage.R
import com.example.loginpage.ui.component.ButtonComponent
import com.example.loginpage.ui.component.ClickableLoginOrLogOutText
import com.example.loginpage.ui.component.DividerTextComponent
import com.example.loginpage.ui.component.GeneralClickableTextComponent
import com.example.loginpage.ui.component.GoogleSignInScreen
import com.example.loginpage.ui.component.HeadingTextComponent
import com.example.loginpage.ui.component.MyPasswordFieldComponent
import com.example.loginpage.ui.component.MyTextFieldComponent
import com.example.loginpage.ui.component.NormalTextComponent
import com.example.loginpage.ui.component.TopAppBarBeforeLogin

@Composable
fun Login(navController: NavHostController,
          homeViewModel: HomeViewModel,
          googleSignInViewModel: GoogleSignInViewModel,
          signUpPageViewModel: SignUpPageViewModel = hiltViewModel()){

    val scrollState = rememberScrollState()
    val googleSignInState = googleSignInViewModel.googleState.value

    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center){
        ScaffoldLoginWithTopBar(navController = navController,
            homeViewModel = homeViewModel,
            googleSignInViewModel = googleSignInViewModel,
            scrollState, signUpPageViewModel)
        if (signUpPageViewModel.signInSignUpInProgress.value ||
            googleSignInState.loading) {
            CircularProgressIndicator()
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldLoginWithTopBar(navController: NavHostController,
                            homeViewModel: HomeViewModel,
                            googleSignInViewModel: GoogleSignInViewModel = hiltViewModel(),
                            scrollState: ScrollState, signUpPageViewModel: SignUpPageViewModel
){
    Scaffold(
        topBar = { TopAppBarBeforeLogin(
            navController = navController, stringResource(id = R.string.master_title),
            false, action = "Fill the email and password above to login.",
            homeViewModel = homeViewModel,
            screenName = "Login")},
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
                HeadingTextComponent(value = "Sign in")

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
                    action = "Login"
                )

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
                    errorStatus = signUpPageViewModel.signUpPageUIState.value.passwordError
                )
                Spacer(modifier = Modifier.height(50.dp))

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

                Spacer(modifier = Modifier.height(20.dp))

                GeneralClickableTextComponent(
                    value = stringResource(id = R.string.home),
                    navController = navController, 7)

                Spacer(modifier = Modifier.height(20.dp))

                GeneralClickableTextComponent(
                    value = "Forget Password?",
                    navController = navController, 2)

                Spacer(modifier = Modifier.height(20.dp))

                DividerTextComponent(type = "Login")

                Spacer(modifier = Modifier.height(20.dp))

                NormalTextComponent(value = "Sign in with:")

                Row() {
                    Spacer(modifier = Modifier.width(10.dp))
                    GoogleSignInScreen(googleSignInViewModel = googleSignInViewModel,
                        homeViewModel = homeViewModel,
                        value = stringResource(id = R.string.google),
                        navController = navController)
                    Spacer(modifier = Modifier.width(5.dp))
                }

                Spacer(modifier = Modifier.height(20.dp))

                DividerTextComponent(type = "Login")

                Spacer(modifier = Modifier.height(20.dp))

                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 40.dp),
                    contentAlignment = Alignment.Center) {
                    val initialText = stringResource(id = R.string.not_registered)
                    val loginText = stringResource(id = R.string.create_account)
                    ClickableLoginOrLogOutText(navController, initialText, loginText, rank = 1)
                }
            }
        }
    )
}