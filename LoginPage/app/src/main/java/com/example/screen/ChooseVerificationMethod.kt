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
import com.example.component.CustomTopAppBar
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
    var chooseVerificationMethod by rememberSaveable { mutableStateOf("") }

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
                Text(
                    modifier = Modifier
                         .padding(bottom = 70.dp),
                    text = "Choose Verification Method",
                    fontSize = 25.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(20.dp))
                Card(modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth(),
                    elevation = 10.dp,
                    border = BorderStroke(1.dp, Color.Black),
                    shape = RoundedCornerShape(10.dp)
                    ) {
                    Text(
                        modifier = Modifier
                            .padding(38.dp),
                        text = "Authenticator App",
                        fontSize = 20.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Justify
                    )
                    VerificationButton(name = "Enter Code", navController = navController, 0)
                }

                Spacer(modifier = Modifier.height(20.dp))
                Card(modifier = Modifier

                    .height(100.dp)
                    .fillMaxWidth(),
                    border = BorderStroke(1.dp, Color.Black),
                    elevation = 10.dp,
                    shape = RoundedCornerShape(10.dp)
                     ) {
                    Text(
                        modifier = Modifier
                            .padding(38.dp),
                        text = "SMS Verification",
                        fontSize = 20.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Justify
                    )
                    VerificationButton(name = "Send Text", navController = navController, 1)
                }

                Spacer(modifier = Modifier.height(20.dp))
                Card(modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth(),
                    elevation = 10.dp,
                    border = BorderStroke(1.dp, Color.Black),
                    shape = RoundedCornerShape(10.dp)) {
                    Text(
                        modifier = Modifier
                            .padding(38.dp),
                        text = "Email Verification",
                        fontSize = 20.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Justify
                    )
                    VerificationButton(name = "Send Email", navController = navController, 2)
                }

                Spacer(modifier = Modifier.height(20.dp))
                Card(modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()) {
                 }
            }
        }
    )
}

@Composable
fun VerificationButton(name: String, navController: NavHostController, navValue: Int){
    Box {
        Button(
            onClick = {
                //TODO ask the user to input correct username and password
                // if the provided one is incorrect
                      if(navValue == 0){
                          navController.navigate(Routes.AuthenticatorAppVerification.route)
                      }else if (navValue == 1){
                          navController.navigate(Routes.SMSVerification.route)
                      }else if (navValue == 2){
                          navController.navigate(Routes.MFAVerifyEmail.route)
                      }
            },
            shape = RoundedCornerShape(50.dp),
            modifier = Modifier
                .height(50.dp)
                .padding(40.dp, 0.dp, 40.dp, 0.dp)
                .align(Alignment.CenterEnd)
        ) {
            Text(text = name,
                color = Color.White)
        }
    }
}