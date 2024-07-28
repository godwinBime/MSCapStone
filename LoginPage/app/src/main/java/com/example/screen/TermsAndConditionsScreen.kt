package com.example.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.component.NormalTextComponent
import com.example.component.TopAppBarBeforeLogin
import com.example.data.viewmodel.SignUpPageViewModel
import com.example.loginpage.R


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TermsAndConditionsScreen(navController: NavHostController,
                             signUpPageViewModel: SignUpPageViewModel
){
    val title = stringResource(id = R.string.terms_and_conditions_header)
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = { TopAppBarBeforeLogin(navController = navController, title = title,
            showBackIcon = true, action = "Read the terms and conditions before proceeding.")
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White)
                    .padding(20.dp)
            ) {
                ScaffoldTermsAndConditionsScreen(navController = navController,
                    signUpPageViewModel = signUpPageViewModel, scrollState = scrollState)
            }
        }
    )
}

@Composable
fun ScaffoldTermsAndConditionsScreen(navController: NavHostController,
                                     signUpPageViewModel: SignUpPageViewModel, scrollState: ScrollState){
    Column(modifier = Modifier
        .verticalScroll(scrollState)
        .padding(top = 90.dp)) {
        NormalTextComponent(value = stringResource(id = R.string.terms_and_conditions))
    }
}