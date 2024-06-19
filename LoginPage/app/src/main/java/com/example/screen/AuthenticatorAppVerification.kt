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
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
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
import com.example.loginpage.ui.theme.LoginPageTheme

class AuthenticatorAppVerificationActivity : ComponentActivity() {
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
fun AuthenticatorAppVerification(navController: NavHostController, signUpPageViewModel: SignUpPageViewModel){
    Box(modifier = Modifier.fillMaxSize()){
        ScaffoldAuthenticatorAppVerification(navController, signUpPageViewModel)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldAuthenticatorAppVerification(navController: NavHostController, signUpPageViewModel: SignUpPageViewModel){
    val verificationCode = stringResource(id = R.string.code)
    val verify = stringResource(id = R.string.verify)

    val codePainterResource = painterResource(id = R.drawable.confirmation_number)

    Scaffold(
        topBar = { CustomTopAppBar(navController, "Auth App Verify", true) },
        content = {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                HeadingTextComponent(value = "Enter Authenticator app code")

                Spacer(modifier = Modifier
                    .height(20.dp))
                MyTextFieldComponent(labelValue = verificationCode,
                    painterResource = codePainterResource,
                    onTextChanged = {
                        signUpPageViewModel.onSignUpEvent(SignUpPageUIEvent.VerificationCodeChanged(it))
                    },
                    errorStatus = signUpPageViewModel.signUpPageUIState.value.verificationCodeError
                    )

                Spacer(modifier = Modifier.height(20.dp))
                SubButton(navController = navController,
                    value = verify, rank = 5,
                    signUpPageViewModel = signUpPageViewModel,
                    isEnable = signUpPageViewModel.verificationCodeValidationsPassed.value
                )
            }
        }
    )
}