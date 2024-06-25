package com.example.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.component.getToast
import com.example.data.home.HomeViewModel
import com.example.data.signup.SignUpPageViewModel
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
import com.example.screen.Settings
import com.example.screen.SignUp
import com.example.screen.TermsAndConditionsScreen
import com.example.screen.UpdateProfile

@Composable
fun ScreenMain(homeViewModel: HomeViewModel = viewModel()){
    val context = LocalContext.current
    val navController = rememberNavController()
    var startDestination = Routes.Login.route
    homeViewModel.checkForActiveSession()

    if (homeViewModel.isUserLoggedIn.value == true){
        getToast(context, "Active user detected", Toast.LENGTH_LONG)
        startDestination = Routes.Home.route
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Routes.Login.route){
            Login(navController = navController, signUpPageViewModel = SignUpPageViewModel())
        }

        composable(Routes.ForgotPassword.route){
            ForgotPassword(navController = navController, signUpPageViewModel = SignUpPageViewModel())
        }

        composable(Routes.SignUp.route){
            SignUp(navController = navController, signUpPageViewModel = SignUpPageViewModel())
        }
        
        composable(Routes.ChangePasswordVerifyEmail.route){
            ChangePasswordVerifyEmail(navController = navController, signUpPageViewModel = SignUpPageViewModel())
        }
        
        composable(Routes.Home.route){
            Home(navController = navController, homeViewModel = HomeViewModel(),
                signUpPageViewModel = SignUpPageViewModel())
        }

        composable(Routes.NewPassword.route){
            NewPassword(navController = navController, signUpPageViewModel = SignUpPageViewModel())
        }

        composable(Routes.UpdateProfile.route){
            UpdateProfile(navController = navController, signUpPageViewModel = SignUpPageViewModel(),
                homeViewModel = HomeViewModel())
        }

        composable(Routes.ChooseVerificationMethod.route){
            ChooseVerificationMethod(navController = navController, 
                signUpPageViewModel = SignUpPageViewModel()
            )
        }

        composable(Routes.AuthenticatorAppVerification.route){
            AuthenticatorAppVerification(navController = navController, signUpPageViewModel = SignUpPageViewModel())
        }

        composable(Routes.SMSVerification.route){
            SMSVerification(navController = navController, signUpPageViewModel = SignUpPageViewModel())
        }

        composable(Routes.MFAVerifyEmail.route){
            MFAVerifyEmail(navController = navController, signUpPageViewModel = SignUpPageViewModel())
        }

        composable(Routes.TermsAndConditionsScreen.route){
            TermsAndConditionsScreen(navController = navController,
                signUpPageViewModel = SignUpPageViewModel()
            )
        }

        composable(Routes.PrivacyPolicy.route){
            PrivacyPolicy(navController = navController)
        }

        composable(Routes.Settings.route){
            Settings(navController = navController, homeViewModel = HomeViewModel(),
                signUpPageViewModel = SignUpPageViewModel())
        }
    }
}