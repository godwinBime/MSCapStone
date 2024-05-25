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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.component.CustomTopAppBar
import com.example.loginpage.Routes
import com.example.loginpage.ui.theme.LoginPageTheme


class AuthenticatorCodeActivity : ComponentActivity() {
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
fun AuthenticatorCode(navController: NavHostController){
    Box(modifier = Modifier.fillMaxSize()){
        ScaffoldAuthenticatorCode(navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldAuthenticatorCode(navController: NavHostController){
    var verificationCode by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = { CustomTopAppBar(navController, "Authenticate User", true) },
        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Enter the code from your authenticator app.",
                    fontSize = 20.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    label = { Text(text = "code") },
                    value = verificationCode,
                    onValueChange = {verificationCode = it},
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ))

                Spacer(modifier = Modifier.height(20.dp))
                Box(modifier = Modifier.padding(200.dp, 20.dp, 40.dp, 490.dp)){
                    Button(
                        onClick = {navController.navigate(Routes.NewPassword.route)},
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
//                            .fillMaxSize()
                            .height(50.dp),
                    ){
                        Text(text = "Verify")
                    }
                }
            }
        }
    )
}