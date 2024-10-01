package com.example.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.data.viewmodel.HomeViewModel
import com.example.data.viewmodel.SignUpPageViewModel


@Composable
fun ContinueToPasswordChange(navController: NavHostController,
                             homeViewModel: HomeViewModel = viewModel(),
                             signUpPageViewModel: SignUpPageViewModel = viewModel()){
    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center){
        ScaffoldContinueToPasswordChangeWithTopBar(navController)
        if (signUpPageViewModel.signInSignUpInProgress.value){
            CircularProgressIndicator()
        }
    }
}

@Composable
fun ScaffoldContinueToPasswordChangeWithTopBar(navController: NavHostController){

}