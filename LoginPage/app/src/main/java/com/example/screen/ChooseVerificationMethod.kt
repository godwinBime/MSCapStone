package com.example.screen

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.component.ChooseMFAButton
import com.example.component.CustomTopAppBar
import com.example.component.DesignMFASpace
import com.example.component.HeadingTextComponent
import com.example.loginpage.ui.theme.LoginPageTheme
import com.example.navigation.Routes

class ChooseVerificationMethodActivity : ComponentActivity() {
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
fun ChooseVerificationMethod(navController: NavHostController){
    val scrollState = rememberScrollState()
    Box(modifier = Modifier.fillMaxSize()){
        ScaffoldChooseVerificationMethod(navController = navController, scrollState)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldChooseVerificationMethod(navController: NavHostController, scrollState: ScrollState){
    Scaffold(
        bottomBar = {
            GeneralBottomAppBar(navController)
        },
        topBar = { CustomTopAppBar(navController, "MFA", true) },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .verticalScroll(scrollState)
                    .padding(15.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                HeadingTextComponent(value = "Choose Verification Method")

                Spacer(modifier = Modifier.height(20.dp))
                DesignMFASpace(navController = navController,
                    value = "Authenticator App", buttonType = "Enter Code", rank = 0)

                Spacer(modifier = Modifier.height(20.dp))

                DesignMFASpace(navController = navController, value = "SMS Verification",
                    buttonType = "Send Text", rank = 1)

                Spacer(modifier = Modifier.height(20.dp))

                DesignMFASpace(navController = navController, value = "Email Verification",
                    buttonType = "Send Email", rank = 2)

                Spacer(modifier = Modifier.height(20.dp))
                Card(modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()) {
                 }
            }
        }
    )
}
