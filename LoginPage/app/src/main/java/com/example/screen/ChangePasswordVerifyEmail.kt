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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.component.CustomTopAppBar
import com.example.navigation.Routes
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

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldChangePassword(navController: NavHostController){
    var verificationCode by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = { CustomTopAppBar(navController, "Verify Email", true)},
        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Enter the code we sent to your email.",
                    fontSize = 20.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    label = { Text(text = "code") },
                    value = verificationCode,
                    onValueChange = {verificationCode = it},
                    shape = RoundedCornerShape(20.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ))

                Spacer(modifier = Modifier.height(20.dp))
                Box(modifier = Modifier.fillMaxSize()){
                    Button(
                        onClick = {navController.navigate(Routes.NewPassword.route)},
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .height(50.dp),
                    ){
                        Text(text = "Verify", color = Color.White)
                    }
                }
            }
        }
    )
}