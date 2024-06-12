package com.example.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.component.HeadingTextComponent
import com.example.component.NormalTextComponent
import com.example.loginpage.R


@Composable
fun TermsAndConditionsScreen(navController: NavHostController){
    Surface(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White)
        .padding(20.dp)) {
        ScaffoldTermsAndConditionsScreen(navController = navController)
    }
}


@Composable
fun ScaffoldTermsAndConditionsScreen(navController: NavHostController){
    Column {
        HeadingTextComponent(value = stringResource(id = R.string.terms_and_conditions_header))
        NormalTextComponent(value = stringResource(id = R.string.terms_and_conditions))
    }
}

@Preview()
@Composable
fun Foo(){
    Column(modifier = Modifier
        .background(Color.White)) {
        HeadingTextComponent(value = stringResource(id = R.string.terms_and_conditions_header))
        NormalTextComponent(value = stringResource(id = R.string.terms_and_conditions))
    }
}