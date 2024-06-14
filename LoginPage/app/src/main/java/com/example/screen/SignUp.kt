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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.component.ButtonComponent
import com.example.component.CheckBoxComponent
import com.example.component.ClickableLoginText
import com.example.component.CustomTopAppBar
import com.example.component.DividerTextComponent
import com.example.component.HeadingTextComponent
import com.example.loginpage.R
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
                HeadingTextComponent(value = "Create an Account")

                Spacer(modifier = Modifier
                    .height(20.dp))
                TextField(
                    label = { Text(text = "First Name") },
                    value = firstName,
                    shape = RoundedCornerShape(20.dp),
                    onValueChange = {firstName = it})

                Spacer(modifier = Modifier
                     .height(20.dp))
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
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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

                CheckBoxComponent(value = "SignUp")

                Spacer(modifier = Modifier.height(20.dp))
                Box(modifier = Modifier
                    .padding(55.dp, 0.dp, 55.dp, 0.dp)){
                    ButtonComponent(navController,
                        value = stringResource(id = R.string.signup), 1)
                }

                DividerTextComponent()

                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 40.dp),
                    contentAlignment = Alignment.Center) {
                    val initialText = stringResource(id = R.string.already_have_account)
                    val loginText = stringResource(id = R.string.login)
                    ClickableLoginText(navController, initialText, loginText)
                }
            }
        }
    )
}