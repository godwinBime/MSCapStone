package com.example.navigation

sealed class Routes(val route: String) {
    object Login: Routes("Login")

    object ForgotPassword: Routes("ForgotPassword")

    object SignUp: Routes("SignUp")

    object NewPassword: Routes("NewPassword")

    object Home: Routes("Home")

    object ChangePasswordVerifyEmail: Routes("ChangePasswordVerifyEmail")

    object UpdateProfile: Routes("UpdateProfile")

    object ChooseVerificationMethod: Routes("ChooseVerificationMethod")

    object AuthenticatorAppVerification: Routes("AuthenticatorAppVerification")

    object SMSVerification: Routes("SMSVerification")

    object MFAVerifyEmail: Routes("MFAVerifyEmail")

    object TermsAndConditionsScreen: Routes("TermsAndConditionsScreen")

    object PrivacyPolicy: Routes("PrivacyPolicy")

    object Settings: Routes("Settings")

    object LoginAndSecurity: Routes("LoginAndSecurity")

    object ContinueToPasswordChange: Routes("ContinueToPasswordChange")

    object UserProfile: Routes("UserProfile")

    object DeleteProfile: Routes("DeleteProfile")

    object DeleteProfileVerifyEmail: Routes("DeleteProfileVerifyEmail")

    object UserProfilePicture: Routes("UserProfilePicture")
}