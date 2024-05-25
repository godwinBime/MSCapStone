package com.example.screen

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.loginpage.Routes
import com.example.viewmodel.LoginViewModel

@Composable
fun ScreenMain(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.Login.route) {
        composable(Routes.Login.route){
            Login(navController = navController)
        }

        composable(Routes.ForgotPassword.route){
            ForgotPassword(navController = navController)
        }

        composable(Routes.SignUp.route){
            SignUp(navController = navController)
        }
        
        composable(Routes.VerifyEmail.route){
            VerifyEmail(navController = navController)
        }
        
        composable(Routes.Home.route){
            Home(navController = navController)
        }

        composable(Routes.NewPassword.route){
            NewPassword(navController = navController)
        }

        composable(Routes.UpdateProfile.route){
            UpdateProfile(navController = navController)
        }

        composable(Routes.AuthenticatorCode.route){
            AuthenticatorCode(navController = navController)
        }

        composable(Routes.ChooseVerificationMethod.route){
            ChooseVerificationMethod(navController = navController)
        }
    }
}