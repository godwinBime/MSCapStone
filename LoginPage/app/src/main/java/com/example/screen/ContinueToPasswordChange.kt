package com.example.screen

//noinspection UsingMaterialAndMaterial3Libraries
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.data.viewmodel.HomeViewModel
import com.example.data.viewmodel.SignUpPageViewModel
import com.example.data.viewmodel.VerifyEmailViewModel
import com.example.loginpage.R
import com.example.loginpage.ui.component.NormalTextComponent
import com.example.loginpage.ui.component.SubButton
import com.example.loginpage.ui.component.TopAppBarBeforeLogin
import com.example.loginpage.ui.component.changePasswordEmail
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ContinueToPasswordChange(navController: NavHostController,
                             homeViewModel: HomeViewModel = viewModel(),
                             signUpPageViewModel: SignUpPageViewModel = viewModel()){
    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center){
        ScaffoldContinueToPasswordChangeWithTopBar(navController = navController,
            signUpPageViewModel = signUpPageViewModel)
        if (signUpPageViewModel.signInSignUpInProgress.value){
            CircularProgressIndicator()
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldContinueToPasswordChangeWithTopBar(
    navController: NavHostController,
    verifyEmailViewModel: VerifyEmailViewModel = viewModel(),
    signUpPageViewModel: SignUpPageViewModel = viewModel()
){
    val user = FirebaseAuth.getInstance()
    val userType = signUpPageViewModel.checkUserProvider(user = user.currentUser)
    Scaffold(
        topBar = { TopAppBarBeforeLogin(navController, stringResource(id = R.string.reset_password),
            true, action = "Change Password Instructions") },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(90.dp))
                NormalTextComponent(
                    value = stringResource(R.string.continue_to_password_change) +
                     "\n" + changePasswordEmail + "\n" + stringResource(R.string.password_reset_details))
                Spacer(modifier = Modifier.height(40.dp))
                SubButton(
                    navController = navController,
                    value = stringResource(R.string.acknowledge),
                    rank = 10,
                    isEnable = true,
                    originalPage = "ContinueToPasswordChange.kt",
                    userType = userType
                )
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    )
}