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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.component.ButtonComponent
import com.example.component.CheckBoxComponent
import com.example.component.CustomTopAppBar
import com.example.component.DividerTextComponent
import com.example.component.GeneralClickableTextComponent
import com.example.component.HeadingTextComponent
import com.example.component.NormalTextComponent
import com.example.loginpage.R
import com.example.loginpage.ui.theme.LoginPageTheme


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

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldLoginWithTopBar(navController: NavHostController, scrollState: ScrollState){
    Scaffold(
        topBar = { CustomTopAppBar(navController, "Capstone-2024", true) },
        content = {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var email by rememberSaveable { mutableStateOf("") }
                var password by rememberSaveable { mutableStateOf("") }
                val showPassword = remember { mutableStateOf(false) }

//                Text(
//                    text = "Sign in with existing account",
//                    fontWeight = FontWeight.Bold,
//                    style = TextStyle(fontSize = 25.sp, fontFamily = FontFamily.Default)
//                )
                HeadingTextComponent(value = "Sign in")

                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    value = email,
                    onValueChange = {
                                    email = it
                    },
                    label = { Text(text = "Email") },
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    shape = RoundedCornerShape(20.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )

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
                    onValueChange = { password = it },
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
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
                    }
                )
                CheckBoxComponent(value = stringResource(id = R.string.terms_and_conditions))

                Spacer(modifier = Modifier.height(20.dp))
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                    ButtonComponent(navController, value = stringResource(id = R.string.login), 0)
                }

                Spacer(modifier = Modifier.height(20.dp))

                GeneralClickableTextComponent(
                    value = "Forget Password?",
                    navController = navController, 2)

                DividerTextComponent()

                Spacer(modifier = Modifier.height(20.dp))

                NormalTextComponent(value = "Sign in with:")

                Box(modifier = Modifier.fillMaxSize()) {
                    Spacer(modifier = Modifier.height(20.dp))

                    GeneralClickableTextComponent(
                        value = "SignUp Here",
                        navController = navController, 1)
                }
            }
        }
    )
}