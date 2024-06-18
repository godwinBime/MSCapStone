package com.example.screen

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.component.ButtonComponent
import com.example.component.CustomTopAppBar
import com.example.component.HeadingTextComponent
import com.example.component.MyConfirmPasswordFieldComponent
import com.example.component.MyPasswordFieldComponent
import com.example.data.SignUpPageViewModel
import com.example.data.SignUpPageUIEvent
import com.example.loginpage.R
import com.example.loginpage.ui.theme.LoginPageTheme

class NewPasswordActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginPageTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // TODO: Undecided
                }
            }
        }
    }
}

@Composable
fun NewPassword(navController: NavHostController, signUpPageViewModel: SignUpPageViewModel){
    Box(modifier = Modifier.fillMaxSize()){
        ScaffoldNewPasswordTopBar(navController, signUpPageViewModel)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldNewPasswordTopBar(navController: NavHostController, signUpPageViewModel: SignUpPageViewModel){
    val password = stringResource(id = R.string.password)
    val confirmPassword = stringResource(id = R.string.confirm_password)
    val resetPassword = stringResource(id = R.string.reset_password)

    val passwordPainterResource = painterResource(id = R.drawable.password)


    Scaffold(
        topBar = { CustomTopAppBar(navController, "New Password", true)},
        content = {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Spacer(modifier = Modifier.height(20.dp))
                HeadingTextComponent(value = "Enter a new password")

                Spacer(modifier = Modifier.height(20.dp))
                MyPasswordFieldComponent(labelValue = password,
                    painterResource = passwordPainterResource,
                    onTextChanged = {
                        signUpPageViewModel.onSignUpEvent(SignUpPageUIEvent.PasswordChanged(it))
                    })

                Spacer(modifier = Modifier.height(20.dp))
                MyConfirmPasswordFieldComponent(labelValue = confirmPassword,
                    painterResource = passwordPainterResource,
                    onTextChanged = {
                        signUpPageViewModel.onSignUpEvent(SignUpPageUIEvent.ConfirmPasswordChanged(it))
                    })
                Spacer(modifier = Modifier.height(20.dp))

                Box(modifier = Modifier
                    .padding(0.dp, 5.dp, 0.dp, 445.dp)
                    .fillMaxSize(),){
                    ButtonComponent(navController = navController,
                        value = resetPassword, 4,
                        onButtonClicked = {
                            signUpPageViewModel.onSignUpEvent(SignUpPageUIEvent.RegisterButtonClicked)
                        },
                        isEnable = signUpPageViewModel.passwordValidationsPassed.value && signUpPageViewModel.confirmPasswordValidationsPassed.value
                    )
                }
            }
        }
    )
}