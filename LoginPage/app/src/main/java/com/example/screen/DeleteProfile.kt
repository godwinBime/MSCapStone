package com.example.screen

import android.annotation.SuppressLint
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.data.viewmodel.GoogleSignInViewModel
import com.example.data.viewmodel.HomeViewModel
import com.example.data.viewmodel.SignUpPageViewModel
import com.example.loginpage.R
import com.example.loginpage.ui.component.DividerTextComponent
import com.example.loginpage.ui.component.DrawerContentComponent
import com.example.loginpage.ui.component.GeneralBottomAppBar
import com.example.loginpage.ui.component.HomeScreenTopAppBar
import com.example.loginpage.ui.component.NormalTextComponent
import com.example.loginpage.ui.component.SubButton
import com.example.loginpage.ui.component.getToast
import kotlinx.coroutines.launch


@Composable
fun DeleteProfile(navController: NavHostController, homeViewModel: HomeViewModel = viewModel(),
                  signUpPageViewModel: SignUpPageViewModel = viewModel(),
                  googleSignInViewModel: GoogleSignInViewModel = hiltViewModel()){
    val scrollState = rememberScrollState()
    val googleSignInState = googleSignInViewModel.googleState.value
    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center){
        ScaffoldDeleteProfileWithTopBar(navController, homeViewModel, scrollState)
        if (signUpPageViewModel.signInSignUpInProgress.value || googleSignInState.loading){
            CircularProgressIndicator()
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldDeleteProfileWithTopBar(navController: NavHostController,
                                     homeViewModel: HomeViewModel,
                                     scrollState: ScrollState,
                                     signUpPageViewModel: SignUpPageViewModel = viewModel()) {
    val context = LocalContext.current
    val warning = "\n${stringResource(R.string.delete_warning)}"
    val delete = stringResource(id = R.string.delete_profile)
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

//    homeViewModel.getUserData(signUpPageViewModel = signUpPageViewModel)
    signUpPageViewModel.fetchedUSerData(signUpPageViewModel = signUpPageViewModel)

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            GeneralBottomAppBar(navController)
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = { getToast(context, "Add floating button clicked!") },
                shape = RoundedCornerShape(12.dp),
                //containerColor = Color(0xff344ceb)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        },
        topBar = {
            HomeScreenTopAppBar(navController, delete, action = "DeleteProfile Screen",
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
                headerTitle = stringResource(id = R.string.delete_profile)
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                NormalTextComponent(value = warning, action = "DeleteProfile")
                DividerTextComponent()
                Spacer(modifier = Modifier.height(20.dp))
                NormalTextComponent(value = "Full Names: ${signUpPageViewModel.fullNames} ")
                Spacer(modifier = Modifier.height(10.dp))
                NormalTextComponent(value = "Phone Number: ${signUpPageViewModel.phoneNumber}")
                Spacer(modifier = Modifier.height(20.dp))
                NormalTextComponent(value = "Email: ${signUpPageViewModel.userEmail}")
                Spacer(modifier = Modifier.height(40.dp))
                SubButton(
                    navController = navController,
                    value = stringResource(R.string.delete_profile),
                    rank = 9,
                    isEnable = true,
                    originalPage = "DeleteProfile.kt"
                )
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    )
}
