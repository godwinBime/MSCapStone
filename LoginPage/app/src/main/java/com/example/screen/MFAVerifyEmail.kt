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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.component.CustomTopAppBar
import com.example.component.HeadingTextComponent
import com.example.component.MyTextFieldComponent
import com.example.component.SubButton
import com.example.loginpage.R
import com.example.loginpage.ui.theme.LoginPageTheme
import com.example.navigation.Routes

class MFAVerifyEmail: ComponentActivity() {
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
fun MFAVerifyEmail(navController: NavHostController){
    Box(modifier = Modifier.fillMaxSize()){
        ScaffoldMFAVerifyEmail(navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldMFAVerifyEmail(navController: NavHostController){
    val verificationCode = stringResource(id = R.string.code)
    val verify = stringResource(id = R.string.verify)

    val codePainterResource = painterResource(id = R.drawable.confirmation_number)

    Scaffold(
        topBar = { CustomTopAppBar(navController, "MFA Email Verify", true) },
        content = {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Spacer(modifier = Modifier.height(20.dp))
                HeadingTextComponent(value = "Enter Verification code")

                Spacer(modifier = Modifier.height(20.dp))
                MyTextFieldComponent(labelValue = verificationCode, painterResource = codePainterResource)

                Spacer(modifier = Modifier.height(20.dp))
                SubButton(navController = navController, value = verify, rank = 5)
            }
        }
    )
}