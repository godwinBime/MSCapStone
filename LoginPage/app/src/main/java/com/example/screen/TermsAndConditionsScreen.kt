package com.example.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.component.CustomTopAppBar
import com.example.component.HeadingTextComponent
import com.example.component.NormalTextComponent
import com.example.loginpage.R


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TermsAndConditionsScreen(navController: NavHostController){
    val title = stringResource(id = R.string.terms_and_conditions_header)
    Scaffold(
//        topBar = { CustomTopAppBar(navController = navController, title = title, showBackIcon = true)},
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White)
                    .padding(20.dp)
            ) {
                ScaffoldTermsAndConditionsScreen(navController = navController)
            }
        }
    )
}


@Composable
fun ScaffoldTermsAndConditionsScreen(navController: NavHostController){
    Column(modifier = Modifier
        .padding(top = 90.dp)) {
        NormalTextComponent(value = stringResource(id = R.string.terms_and_conditions))
    }
}