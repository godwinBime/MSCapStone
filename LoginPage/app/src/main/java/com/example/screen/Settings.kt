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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.loginpage.ui.component.DrawerContentComponent
import com.example.loginpage.ui.component.GeneralBottomAppBar
import com.example.loginpage.ui.component.HomeScreenTopAppBar
import com.example.loginpage.ui.component.NormalTextComponent
import com.example.loginpage.ui.component.getToast
import com.example.data.viewmodel.HomeViewModel
import com.example.data.viewmodel.SignUpPageViewModel
import com.example.loginpage.R
import kotlinx.coroutines.launch

@Composable
fun Settings(navController: NavHostController, homeViewModel: HomeViewModel = viewModel(),
             signUpPageViewModel: SignUpPageViewModel = viewModel()){
    val scrollState = rememberScrollState()
    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center){
        ScaffoldSettingsScreenWithTopBar(navController, homeViewModel, scrollState)
        if (signUpPageViewModel.signInSignUpInProgress.value){
            CircularProgressIndicator()
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldSettingsScreenWithTopBar(navController: NavHostController,
                                     homeViewModel: HomeViewModel,
                                     scrollState: ScrollState,
                                     signUpPageViewModel: SignUpPageViewModel = viewModel()){
    val context = LocalContext.current
    val name = "\nSettings for ${signUpPageViewModel.fullNames.substringBefore(" ")}"
    val home = stringResource(id = R.string.settings)
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

//    homeViewModel.getUserData(signUpPageViewModel = signUpPageViewModel)

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            GeneralBottomAppBar(navController)
        },

        floatingActionButton = {
            FloatingActionButton(onClick = { getToast(context, "Add floating button clicked!") },
                shape = RoundedCornerShape(12.dp),
                //containerColor = Color(0xff344ceb)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        },
        topBar = { HomeScreenTopAppBar(navController, home, action = "Settings Screen",
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
                headerTitle = stringResource(id = R.string.settings)
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
                NormalTextComponent(value = "Welcome, $name")

                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    )
}