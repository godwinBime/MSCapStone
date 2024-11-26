package com.example.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.data.uistate.UserData
import com.example.data.viewmodel.GoogleSignInViewModel
import com.example.data.viewmodel.HomeViewModel
import com.example.data.viewmodel.SignUpPageViewModel
import com.example.data.viewmodel.TimerViewModel
import com.example.data.viewmodel.VerifyEmailViewModel
import com.example.loginpage.R
import com.example.loginpage.ui.component.DrawerContentComponent
import com.example.loginpage.ui.component.GeneralBottomAppBar
import com.example.loginpage.ui.component.GoogleAccountProfilePictureComponent
import com.example.loginpage.ui.component.HomeScreenTopAppBar
import com.example.loginpage.ui.component.LoadingScreenComponent
import com.example.loginpage.ui.component.NormalTextComponent
import com.example.loginpage.ui.component.PhotoPickerComponent
import com.example.loginpage.ui.component.getToast
import com.example.navigation.Routes
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@Composable
fun Home(navController: NavHostController,
         homeViewModel: HomeViewModel = viewModel(),
         googleSignInViewModel: GoogleSignInViewModel = hiltViewModel(),
         signUpPageViewModel: SignUpPageViewModel = viewModel()){
    val scrollState = rememberScrollState()
    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center){
        ScaffoldHomeScreenWithTopBar(navController, homeViewModel, scrollState)
        LoadingScreenComponent(googleSignInViewModel = googleSignInViewModel,
            signUpPageViewModel = signUpPageViewModel)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldHomeScreenWithTopBar(navController: NavHostController,
                                 homeViewModel: HomeViewModel = viewModel(),
                                 scrollState: ScrollState,
                                 verifyEmailViewModel: VerifyEmailViewModel = viewModel(),
                                 timerViewModel: TimerViewModel = viewModel(),
                                 signUpPageViewModel: SignUpPageViewModel = viewModel()){
//    val context = LocalContext.current
    val home = stringResource(id = R.string.home)
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val user = FirebaseAuth.getInstance().currentUser
    val providerId = signUpPageViewModel.checkUserProvider(user = user)
    if (providerId == "password") {
        signUpPageViewModel.fetchedUSerData(
            signUpPageViewModel = signUpPageViewModel,
            providerId = "password"
        )
    }

    if (timerViewModel.isTimerFinished() || timerViewModel.isMfaCounterFinished()){
        LaunchedEffect(Unit) {
            verifyEmailViewModel.resetOtpCode()
            timerViewModel.resetTimer()
            timerViewModel.mfaResetTimer()
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            GeneralBottomAppBar(
                navController = navController, providerId = providerId,
                trueIndex = 0)
        },
        topBar = {
            HomeScreenTopAppBar(navController = navController,
                title = home,
                action = "Home Screen",
            navigationIconClicked = {
                coroutineScope.launch {
                    scaffoldState.drawerState.open()
                }
            }
        )
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen, /*Gesture is on enabled when drawer is in open state*/

        drawerContent = {
            DrawerContentComponent(
                navController = navController,
                homeViewModel = homeViewModel
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Spacer(modifier = Modifier.height(80.dp))
                if (providerId == "password") {
//                    PhotoPickerComponent(navController = navController)
                    NormalTextComponent(
                        value = "Hi, ${signUpPageViewModel.fullNames.substringBefore(delimiter = " ")}"
                    )
                }else if (providerId == "google.com"){
//                    PhotoPickerComponent(navController = navController)
//                    Spacer(modifier = Modifier.height(80.dp))
//                    GoogleAccountProfilePictureComponent(user = user, size = 120.dp)
                    NormalTextComponent(
                        value = "Hi, ${user?.displayName?.substringBefore(delimiter = " ")}"
                    )
                }else{
                    NormalTextComponent(value = stringResource(id = R.string.no_user_found))
                }
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    )
}