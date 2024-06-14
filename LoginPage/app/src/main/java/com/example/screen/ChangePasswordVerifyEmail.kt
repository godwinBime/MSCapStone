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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.example.loginpage.R
import com.example.loginpage.ui.theme.LoginPageTheme

class ChangePasswordVerifyEmailActivity : ComponentActivity() {
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
fun ChangePasswordVerifyEmail(navController: NavHostController){
    Box(modifier = Modifier.fillMaxSize()){
        ScaffoldChangePassword(navController)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldChangePassword(navController: NavHostController){
    val verificationCode by rememberSaveable { mutableStateOf("") }

    val painterVerificationCode = painterResource(id = R.drawable.confirmation_number)

    Scaffold(
        topBar = { CustomTopAppBar(navController, "Verify Email", true)},
        content = {
            Column(
                modifier = Modifier
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Spacer(modifier = Modifier.height(5.dp))

                HeadingTextComponent(value = "Enter Verification Code")

                Spacer(modifier = Modifier.height(20.dp))
                MyTextFieldComponent(labelValue = verificationCode, painterResource = painterVerificationCode)

                Spacer(modifier = Modifier
                    .height(20.dp))
                val verify = stringResource(id = R.string.verify)
                SubButton(navController = navController, value = verify, 3)
            }
        }
    )
}