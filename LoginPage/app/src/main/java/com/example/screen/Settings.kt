package com.example.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
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
import com.example.data.viewmodel.GoogleSignInViewModel
import com.example.loginpage.ui.component.DrawerContentComponent
import com.example.loginpage.ui.component.GeneralBottomAppBar
import com.example.loginpage.ui.component.HomeScreenTopAppBar
import com.example.loginpage.ui.component.NormalTextComponent
import com.example.loginpage.ui.component.getToast
import com.example.data.viewmodel.HomeViewModel
import com.example.data.viewmodel.SignUpPageViewModel
import com.example.loginpage.R
import com.example.loginpage.ui.component.LoadingScreenComponent
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@Composable
fun Settings(navController: NavHostController,
             homeViewModel: HomeViewModel = viewModel(),
             signUpPageViewModel: SignUpPageViewModel = viewModel(),
             googleSignInViewModel: GoogleSignInViewModel = hiltViewModel()){
    val scrollState = rememberScrollState()
    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center){
        ScaffoldSettingsScreenWithTopBar(
            navController = navController,
            scrollState = scrollState)
        LoadingScreenComponent(googleSignInViewModel = googleSignInViewModel,
            signUpPageViewModel = signUpPageViewModel)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldSettingsScreenWithTopBar(navController: NavHostController,
                                     scrollState: ScrollState,
                                     homeViewModel: HomeViewModel = viewModel(),
                                     signUpPageViewModel: SignUpPageViewModel = viewModel()){
    val user = FirebaseAuth.getInstance().currentUser
    val providerId = signUpPageViewModel.checkUserProvider(user = user)
    val context = LocalContext.current

    if (providerId == "password") {
        signUpPageViewModel.fetchedUSerData(signUpPageViewModel = signUpPageViewModel,
            providerId = "password", context = context)
    }
    val name = "\nSettings for ${
        if (providerId == "password") {
            signUpPageViewModel.fullNames.substringBefore(" ")
        }else{
            user?.displayName?.substringBefore(" ")
        }
    }"
    val settingTitle = stringResource(id = R.string.settings)
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            GeneralBottomAppBar(
                navController = navController, providerId = providerId,
                trueIndex = 4)
        },
        topBar = { HomeScreenTopAppBar(navController = navController, title = settingTitle,
            action = "Settings Screen",
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
                homeViewModel = homeViewModel,
//                headerTitle = stringResource(id = R.string.settings)
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
                if (user != null) {
                    NormalTextComponent(value = name)
                }else{
                    NormalTextComponent(value = stringResource(id = R.string.no_user_found))
                }
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    )
}