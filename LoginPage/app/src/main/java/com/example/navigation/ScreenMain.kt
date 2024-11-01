package com.example.navigation

import android.widget.Toast
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.data.repository.AuthenticationRepositoryImpl
import com.example.data.viewmodel.GoogleSignInViewModel
import com.example.data.viewmodel.HomeViewModel
import com.example.data.viewmodel.SignUpPageViewModel
import com.example.loginpage.MainActivity
import com.example.loginpage.ui.component.getToast
import com.example.screen.AuthenticatorAppVerification
import com.example.screen.ChangePasswordVerifyEmail
import com.example.screen.ChooseVerificationMethod
import com.example.screen.ContinueToPasswordChange
import com.example.screen.DeleteProfile
import com.example.screen.DeleteProfileVerifyEmail
import com.example.screen.ForgotPassword
import com.example.screen.Home
import com.example.screen.Login
import com.example.screen.LoginAndSecurity
import com.example.screen.MFAVerifyEmail
import com.example.screen.NewPassword
import com.example.screen.PrivacyPolicy
import com.example.screen.SMSVerification
import com.example.screen.Settings
import com.example.screen.SignUp
import com.example.screen.TermsAndConditionsScreen
import com.example.screen.UpdateProfile
import com.example.screen.UserProfile
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ScreenMain(homeViewModel: HomeViewModel = viewModel(),
               signUpPageViewModel: SignUpPageViewModel = viewModel()){
    val context = LocalContext.current
    val navController = rememberNavController()
    var startDestination = Routes.Login.route
    homeViewModel.checkForActiveSession()
    val user = FirebaseAuth.getInstance().currentUser
    val providerId = signUpPageViewModel.checkUserProvider(user = user)

    if (homeViewModel.isUserLoggedIn.value == true && providerId == "google.com"){
        getToast(context, "Active Google user detected", Toast.LENGTH_LONG)
        startDestination = Routes.Home.route
    }else if (homeViewModel.isUserLoggedIn.value == true && providerId == "password"){
        getToast(context, "Partially Active Email/Password user detected", Toast.LENGTH_LONG)
//        startDestination = Routes.ChooseVerificationMethod.route
        startDestination = Routes.Home.route
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Routes.Login.route){
            Login(navController = navController,
                homeViewModel =  HomeViewModel(),
                signUpPageViewModel = SignUpPageViewModel(),
                googleSignInViewModel = GoogleSignInViewModel(
                    repository = AuthenticationRepositoryImpl(FirebaseAuth.getInstance()))
            )
        }

        composable(Routes.ForgotPassword.route){
            ForgotPassword(navController = navController,
                homeViewModel = HomeViewModel(), signUpPageViewModel = SignUpPageViewModel()
            )
        }

        composable(Routes.SignUp.route){
            SignUp(navController = navController,
                homeViewModel = HomeViewModel(),
                signUpPageViewModel = SignUpPageViewModel()
            )
        }
        
        composable(Routes.ChangePasswordVerifyEmail.route){
            ChangePasswordVerifyEmail(navController = navController,
                homeViewModel = HomeViewModel(), signUpPageViewModel = SignUpPageViewModel()
            )
        }
        
        composable(Routes.Home.route){
            Home(navController = navController, homeViewModel = HomeViewModel(),
                signUpPageViewModel = SignUpPageViewModel())
        }

        composable(Routes.NewPassword.route){
            NewPassword(navController = navController, signUpPageViewModel = SignUpPageViewModel(),
                homeViewModel = HomeViewModel()
            )
        }

        composable(Routes.UpdateProfile.route){
            UpdateProfile(navController = navController,
                signUpPageViewModel = SignUpPageViewModel(),
                homeViewModel = HomeViewModel(),
                googleSignInViewModel = GoogleSignInViewModel(
                    repository = AuthenticationRepositoryImpl(FirebaseAuth.getInstance())
                )
            )
        }

        composable(Routes.ChooseVerificationMethod.route){
            ChooseVerificationMethod(navController = navController, 
                signUpPageViewModel = SignUpPageViewModel(),
                homeViewModel = HomeViewModel()
            )
        }

        composable(Routes.AuthenticatorAppVerification.route){
            AuthenticatorAppVerification(navController = navController,
                homeViewModel = HomeViewModel(),
                signUpPageViewModel = SignUpPageViewModel()
            )
        }

        composable(Routes.SMSVerification.route){
            SMSVerification(navController = navController,
                homeViewModel = HomeViewModel(),
                signUpPageViewModel = SignUpPageViewModel()
            )
        }

        composable(Routes.MFAVerifyEmail.route){
            MFAVerifyEmail(navController = navController,
                homeViewModel = HomeViewModel(),
                signUpPageViewModel = SignUpPageViewModel()
            )
        }

        composable(Routes.TermsAndConditionsScreen.route){
            TermsAndConditionsScreen(navController = navController,
                signUpPageViewModel = SignUpPageViewModel(),
                homeViewModel = HomeViewModel()
            )
        }

        composable(Routes.PrivacyPolicy.route){
            PrivacyPolicy(navController = navController)
        }

        composable(Routes.Settings.route){
            Settings(navController = navController, homeViewModel = HomeViewModel(),
                signUpPageViewModel = SignUpPageViewModel())
        }

        composable(Routes.LoginAndSecurity.route){
            LoginAndSecurity(
                navController = navController, homeViewModel = HomeViewModel(),
                signUpPageViewModel = SignUpPageViewModel(),
                mainActivity = MainActivity()
            )
        }

        composable(Routes.ContinueToPasswordChange.route){
            ContinueToPasswordChange(
                navController = navController
            )
        }

        composable(Routes.UserProfile.route){
            UserProfile(navController = navController,
                googleSignInViewModel = GoogleSignInViewModel(
                    repository = AuthenticationRepositoryImpl(FirebaseAuth.getInstance())
                ))
        }

        composable(Routes.DeleteProfile.route){
            DeleteProfile(navController = navController,
                googleSignInViewModel = GoogleSignInViewModel(
                    repository = AuthenticationRepositoryImpl(FirebaseAuth.getInstance())
                ))
        }

        composable(Routes.DeleteProfileVerifyEmail.route){
            DeleteProfileVerifyEmail(navController = navController,
                homeViewModel = HomeViewModel(),
                signUpPageViewModel = SignUpPageViewModel()
            )
        }
    }
}