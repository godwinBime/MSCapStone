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
import androidx.compose.foundation.layout.fillMaxHeight
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
import com.example.component.GeneralClickableTextComponent
import com.example.component.HeadingTextComponent
import com.example.component.MyPasswordFieldComponent
import com.example.component.MyTextFieldComponent
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

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldLoginWithTopBar(navController: NavHostController, scrollState: ScrollState){
    Scaffold(
        topBar = { CustomTopAppBar(navController, "Capstone-2024", true) },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(20.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HeadingTextComponent(value = "Sign in")

                Spacer(modifier = Modifier.height(20.dp))

                val email = stringResource(id = R.string.email)
                val emailPainterResource = painterResource(id = R.drawable.email)

                MyTextFieldComponent(labelValue = email, painterResource = emailPainterResource)

                Spacer(modifier = Modifier.height(20.dp))
                val password = stringResource(id = R.string.password)
                val passwordPainterResource = painterResource(id = R.drawable.password)

                MyPasswordFieldComponent(labelValue = password, painterResource = passwordPainterResource)

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

                DividerTextComponent()

                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 40.dp),
                    contentAlignment = Alignment.Center) {
                    val initialText = stringResource(id = R.string.not_registered)
                    val loginText = stringResource(id = R.string.create_account)
                    ClickableLoginOrLogOutText(navController, initialText, loginText)
                }
            }
        }
    )
}