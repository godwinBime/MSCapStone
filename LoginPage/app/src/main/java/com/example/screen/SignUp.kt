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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.component.ButtonComponent
import com.example.component.CheckBoxComponent
import com.example.component.ClickableLoginOrLogOutText
import com.example.component.CustomTopAppBar
import com.example.component.DividerTextComponent
import com.example.component.HeadingTextComponent
import com.example.component.MyConfirmPasswordFieldComponent
import com.example.component.MyPasswordFieldComponent
import com.example.component.MyTextFieldComponent
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
    val firstName = stringResource(id = R.string.first_name)
    val lastName = stringResource(id = R.string.last_name)
    val email = stringResource(id = R.string.email)
    val phoneNumber = stringResource(id = R.string.phone_number)
    val password = stringResource(id = R.string.password)
    val confirmPassword = stringResource(id = R.string.confirm_password)

    val emailPainterResource = painterResource(id = R.drawable.email)
    val personPainterResource = painterResource(id = R.drawable.person)
    val phoneNumberPainterResource = painterResource(id = R.drawable.phone)
    val passwordPainterResource = painterResource(id = R.drawable.password)
    val confirmPasswordPainterResource = painterResource(id = R.drawable.password)


    Scaffold(
        topBar = { CustomTopAppBar(navController, "Create Account", true)},
        content = {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                HeadingTextComponent(value = "Create an Account")

                Spacer(modifier = Modifier
                    .height(20.dp))
                MyTextFieldComponent(labelValue = firstName, painterResource = personPainterResource)

                Spacer(modifier = Modifier
                     .height(20.dp))
                MyTextFieldComponent(labelValue = lastName, painterResource = personPainterResource)

                Spacer(modifier = Modifier.height(20.dp))

                MyTextFieldComponent(labelValue = email, painterResource = emailPainterResource)


                Spacer(modifier = Modifier.height(20.dp))
                MyTextFieldComponent(labelValue = phoneNumber, painterResource = phoneNumberPainterResource)

                Spacer(modifier = Modifier.height(20.dp))
                MyPasswordFieldComponent(labelValue = password, painterResource = passwordPainterResource)

                Spacer(modifier = Modifier.height(20.dp))
                MyConfirmPasswordFieldComponent(labelValue = confirmPassword, painterResource = confirmPasswordPainterResource)

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
                    ClickableLoginOrLogOutText(navController, initialText, loginText)
                }
            }
        }
    )
}