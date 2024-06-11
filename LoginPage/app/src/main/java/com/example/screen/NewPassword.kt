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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.component.CustomTopAppBar
import com.example.navigation.Routes
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
fun NewPassword(navController: NavHostController){
    Box(modifier = Modifier.fillMaxSize()){
        ScaffoldNewPasswordTopBar(navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldNewPasswordTopBar(navController: NavHostController){
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    val showPassword = remember { mutableStateOf(false) }
    val confirmShowPassword = remember { mutableStateOf(false) }

    Scaffold(
        topBar = { CustomTopAppBar(navController, "New Password", true)},
        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = "Enter a new password",
                    fontSize = 20.sp,
                    color = Color.Black)

                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    label = { androidx.compose.material3.Text(text = "Password") },
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
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ))

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
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
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
                Box(modifier = Modifier.fillMaxSize(),){
                    Button(
                        onClick = {navController.navigate(Routes.Login.route)},
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .height(50.dp),
                    ){
                        Text(text = "Reset Password",
                            color = Color.White)
                    }
                }
            }
        }
    )
}