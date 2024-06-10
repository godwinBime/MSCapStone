package com.example.screen

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.component.CustomTopAppBar
import androidx.compose.ui.unit.sp
import com.example.navigation.Routes
import com.example.loginpage.ui.theme.LoginPageTheme

class SignUpActivity : ComponentActivity() {
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
fun SignUp(navController: NavHostController){
    val scrollState = rememberScrollState()
    Box(modifier = Modifier.fillMaxSize()){
        ScaffoldSignUpWithTopBar(navController, scrollState)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldSignUpWithTopBar(navController: NavHostController, scrollState: ScrollState){
    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    val showPassword = remember { mutableStateOf(false) }
    val confirmShowPassword = remember { mutableStateOf(false) }

    Scaffold(
        topBar = { CustomTopAppBar(navController, "Create Account", true)},
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(text = "Welcome,\nCreate an account",
                    fontSize = 25.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                    )

                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    label = { Text(text = "First Name") },
                    value = firstName,
                    shape = RoundedCornerShape(20.dp),
                    onValueChange = {firstName = it})

                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    label = { Text(text = "Last Name") },
                    value = lastName,
                    shape = RoundedCornerShape(20.dp),
                    onValueChange = {lastName = it})

                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    label = { Text(text = "Email") },
                    value = email,
                    shape = RoundedCornerShape(20.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    onValueChange = {email = it})

                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    label = {Text(text = "Phone number") },
                    value = phoneNumber,
                    shape = RoundedCornerShape(20.dp),
                    onValueChange = {phoneNumber = it})

                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    label = { Text(text = "Password") },
                    value = password,
                    visualTransformation =
                    if (showPassword.value){
                        VisualTransformation.None
                    } else{
                        PasswordVisualTransformation()
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    onValueChange = {password = it},
                    shape = RoundedCornerShape(20.dp),
                    trailingIcon = {
                        if (showPassword.value){
                            IconButton(onClick = { showPassword.value = false }) {
                                Icon(
                                    imageVector = Icons.Filled.Visibility,
                                    contentDescription = "Hide Password",
                                    tint = Color.Black
                                )
                            }
                        }else{
                            IconButton(onClick = { showPassword.value = true }) {
                                Icon(
                                    imageVector = Icons.Filled.VisibilityOff,
                                    contentDescription = "Show Password",
                                    tint = Color.Black
                                )
                            }
                        }
                    })

                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    label = { Text(text = "Confirm Password")},
                    value = confirmPassword,
                    visualTransformation =
                    if (confirmShowPassword.value){
                        VisualTransformation.None
                    } else{
                        PasswordVisualTransformation()
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    onValueChange = {confirmPassword = it},
                    shape = RoundedCornerShape(20.dp),
                    trailingIcon = {
                        if (confirmShowPassword.value){
                            IconButton(onClick = { confirmShowPassword.value = false }) {
                                Icon(
                                    imageVector = Icons.Filled.Visibility,
                                    contentDescription = "Hide Password",
                                    tint = Color.Black
                                )
                            }
                        }else{
                            IconButton(onClick = { confirmShowPassword.value = true }) {
                                Icon(
                                    imageVector = Icons.Filled.VisibilityOff,
                                    contentDescription = "Show Password",
                                    tint = Color.Black
                                )
                            }
                        }
                    })

                Spacer(modifier = Modifier.height(20.dp))
                Box(modifier = Modifier.padding(55.dp, 0.dp, 55.dp, 100.dp)){
                    Button(
                        onClick = {
                            navController.navigate(Routes.Login.route)
                            //navController.navigate(Routes.ChooseVerificationMethod.route)

                        },
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .fillMaxSize(0.9f)
                            .height(50.dp)
                    ){
                        Text(text = "Sign Up", color = Color.White)
                    }
                }

                Box(modifier = Modifier.fillMaxSize()) {
                    ClickableText(
                        text = AnnotatedString("Login"),
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(20.dp),
                        onClick = { navController.navigate(Routes.Login.route) },
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