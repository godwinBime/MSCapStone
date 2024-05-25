package com.example.loginpage

sealed class Routes(val route: String) {
    object Login: Routes("Login")
    object ForgotPassword: Routes("ForgotPassword")
    object SignUp: Routes("SignUp")
    object NewPassword: Routes("NewPassword")
    object Home: Routes("Home")

    object VerifyEmail: Routes("VerifyEmail")

    object UpdateProfile: Routes("UpdateProfile")

    object AuthenticatorCode: Routes("AuthenticatorCode")

    object ChooseVerificationMethod: Routes("ChooseVerificationMethod")
}