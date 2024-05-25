package com.example.screen

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.component.CustomTopAppBar
import com.example.loginpage.Routes
import com.example.loginpage.ui.theme.LoginPageTheme
import com.example.viewmodel.LoginViewModel


class LoginActivity : ComponentActivity() {
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
fun Login(navController: NavHostController){
    val scrollState = rememberScrollState()
    Box(modifier = Modifier.fillMaxSize()){
        ScaffoldLoginWithTopBar(navController = navController, scrollState)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldLoginWithTopBar(navController: NavHostController, scrollState: ScrollState){
    Scaffold(
        topBar = { CustomTopAppBar(navController, "Sign In", true) },
        content = {
            Column(
                modifier = Modifier.padding(20.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var userName by rememberSaveable { mutableStateOf("") }
                var password by rememberSaveable { mutableStateOf("") }
                Text(
                    text = "Welcome, Sign in",
                    style = TextStyle(fontSize = 40.sp, fontFamily = FontFamily.Default)
                )

                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    value = userName,
                    onValueChange = {
                                    userName = it
                    },
                    label = { Text(text = "Email") })

                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    label = { Text(text = "Password") },
                    value = password,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    onValueChange = { password = it })

                Spacer(modifier = Modifier.height(20.dp))
                Box(modifier = Modifier.fillMaxSize()) {
                    Button(
                        onClick = {
                            //TODO ask the user to input correct username and password
                            // if the provided one is incorrect

                            navController.navigate(Routes.ChooseVerificationMethod.route)
                                  },
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .height(50.dp)
                            .padding(40.dp, 0.dp, 40.dp, 0.dp)
                            .align(Alignment.TopCenter)
                    ) {
                        Text(text = "Login",
                            color = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(50.dp))
                Box(modifier = Modifier.fillMaxSize()) {
                    ClickableText(
                        text = AnnotatedString("Forgot Password?"),
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(0.dp, 0.dp, 0.dp, 40.dp),
                        onClick = { navController.navigate(Routes.ForgotPassword.route) },
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = FontFamily.Default,
                            textDecoration = TextDecoration.Underline,
                            color = Color.Black
                        )
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    ClickableText(
                        text = AnnotatedString("Sign Up here"),
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(20.dp),
                        onClick = { navController.navigate(Routes.SignUp.route) },
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = FontFamily.Default,
                            textDecoration = TextDecoration.Underline,
                            color = Color.Black
                        )
                    )
                }
            }
        }
    )
}