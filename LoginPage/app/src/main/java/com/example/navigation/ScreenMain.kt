package com.example.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.data.LoginViewModel
import com.example.screen.AuthenticatorAppVerification
import com.example.screen.ChangePasswordVerifyEmail
import com.example.screen.ChooseVerificationMethod
import com.example.screen.ForgotPassword
import com.example.screen.Home
import com.example.screen.Login
import com.example.screen.MFAVerifyEmail
import com.example.screen.NewPassword
import com.example.screen.PrivacyPolicy
import com.example.screen.SMSVerification
import com.example.screen.SignUp
import com.example.screen.TermsAndConditionsScreen
import com.example.screen.UpdateProfile

@Composable
fun ScreenMain(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.Login.route) {
        composable(Routes.Login.route){
            Login(navController = navController, loginViewModel = LoginViewModel())
        }

        composable(Routes.ForgotPassword.route){
            ForgotPassword(navController = navController, loginViewModel = LoginViewModel())
        }

        composable(Routes.SignUp.route){
            SignUp(navController = navController, loginViewModel = LoginViewModel())
        }
        
        composable(Routes.ChangePasswordVerifyEmail.route){
            ChangePasswordVerifyEmail(navController = navController, loginViewModel = LoginViewModel())
        }
        
        composable(Routes.Home.route){
            Home(navController = navController)
        }

        composable(Routes.NewPassword.route){
            NewPassword(navController = navController, loginViewModel = LoginViewModel())
        }

        composable(Routes.UpdateProfile.route){
            UpdateProfile(navController = navController)
        }

        composable(Routes.ChooseVerificationMethod.route){
            ChooseVerificationMethod(navController = navController)
        }

        composable(Routes.AuthenticatorAppVerification.route){
            AuthenticatorAppVerification(navController = navController, loginViewModel = LoginViewModel())
        }

        composable(Routes.SMSVerification.route){
            SMSVerification(navController = navController, loginViewModel = LoginViewModel())
        }

        composable(Routes.MFAVerifyEmail.route){
            MFAVerifyEmail(navController = navController, loginViewModel = LoginViewModel())
        }

        composable(Routes.TermsAndConditionsScreen.route){
            TermsAndConditionsScreen(navController = navController)
        }

        composable(Routes.PrivacyPolicy.route){
            PrivacyPolicy(navController = navController)
        }
    }
}