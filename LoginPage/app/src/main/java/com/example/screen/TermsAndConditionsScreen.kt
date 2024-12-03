package com.example.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.loginpage.ui.component.NormalTextComponent
import com.example.loginpage.ui.component.TopAppBarBeforeLogin
import com.example.data.viewmodel.HomeViewModel
import com.example.data.viewmodel.SignUpPageViewModel
import com.example.loginpage.R
import com.example.loginpage.ui.component.SubButton


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TermsAndConditionsScreen(navController: NavHostController,
                             signUpPageViewModel: SignUpPageViewModel = viewModel(),
                             homeViewModel: HomeViewModel = viewModel()){
    val title = stringResource(id = R.string.terms_and_conditions_header)
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = { TopAppBarBeforeLogin(navController = navController, title = title,
            showBackIcon = true, action = "Read the terms and conditions before proceeding.",
            homeViewModel = homeViewModel)
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
                                     signUpPageViewModel: SignUpPageViewModel = viewModel(),
                                     scrollState: ScrollState){
    Column(modifier = Modifier
        .verticalScroll(scrollState)
        .padding(top = 120.dp)) {
        Spacer(modifier = Modifier.height(30.dp))
        NormalTextComponent(value = stringResource(id = R.string.terms_and_conditions))
        Spacer(modifier = Modifier.height(450.dp))
        Box(modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(12.dp))
            .padding(19.dp),
            contentAlignment = Alignment.Center
            ) {
            SubButton(
                navController = navController,
                value = stringResource(R.string.acknowledge),
                rank = 13,
                signUpPageViewModel = signUpPageViewModel,
                isEnable = true,
                originalPage = "TermsAndConditionsScreen.kt"
            )
        }
    }
}