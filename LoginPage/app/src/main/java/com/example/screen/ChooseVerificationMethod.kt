package com.example.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.ScrollState
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
import androidx.compose.foundation.verticalScroll
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Card
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.loginpage.ui.component.DesignMFASpace
import com.example.loginpage.ui.component.HeadingTextComponent
import com.example.loginpage.ui.component.TopAppBarBeforeLogin
import com.example.data.viewmodel.HomeViewModel
import com.example.data.viewmodel.SignUpPageViewModel

@Composable
fun ChooseVerificationMethod(navController: NavHostController,
                             signUpPageViewModel: SignUpPageViewModel,
                             homeViewModel: HomeViewModel){
    val scrollState = rememberScrollState()
    Box(modifier = Modifier.fillMaxSize()){
        ScaffoldChooseVerificationMethod(navController = navController, scrollState,
            signUpPageViewModel, homeViewModel)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldChooseVerificationMethod(navController: NavHostController,
                                     scrollState: ScrollState,
                                     signUpPageViewModel: SignUpPageViewModel,
                                     homeViewModel: HomeViewModel){
    Scaffold(
        topBar = { TopAppBarBeforeLogin(navController, "MFA",
            true, action = "Choose Verification Method then proceed.",
            homeViewModel, screenName = "ChooseVerificationMethod") },
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
                    value = "Authenticator App", buttonType = "Enter Code", rank = 0,
                    signUpPageViewModel = signUpPageViewModel)

                Spacer(modifier = Modifier.height(30.dp))

                DesignMFASpace(navController = navController, value = "SMS Verification",
                    buttonType = "Send Text", rank = 1,
                    signUpPageViewModel = signUpPageViewModel)

                Spacer(modifier = Modifier.height(40.dp))

                DesignMFASpace(navController = navController, value = "Email Verification",
                    buttonType = "Send Email", rank = 2,
                    signUpPageViewModel = signUpPageViewModel)

                Spacer(modifier = Modifier
                    .height(90.dp))
                Card(modifier = Modifier
//                    .height(90.dp)
                    .fillMaxWidth()) {
                 }
            }
        }
    )
}
