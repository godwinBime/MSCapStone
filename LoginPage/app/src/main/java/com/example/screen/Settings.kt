package com.example.screen

import android.annotation.SuppressLint
import android.util.Log
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
import com.example.component.GeneralBottomAppBar
import com.example.component.HomeScreenDrawerHeader
import com.example.component.HomeScreenTopAppBar
import com.example.component.NavigationDrawerBody
import com.example.component.NormalTextComponent
import com.example.component.getToast
import com.example.data.home.HomeViewModel
import com.example.data.signup.SignUpPageViewModel
import com.example.loginpage.R
import com.example.navigation.Routes
import kotlinx.coroutines.launch

@Composable
fun Settings(navController: NavHostController, homeViewModel: HomeViewModel = viewModel(),
         signUpPageViewModel: SignUpPageViewModel = viewModel()){
    val scrollState = rememberScrollState()
    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center){
        ScaffoldSettingsScreenWithTopBar(navController, homeViewModel, scrollState)
        if (signUpPageViewModel.signINSignUpInProgress.value){
            CircularProgressIndicator()
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldSettingsScreenWithTopBar(navController: NavHostController,
                                 homeViewModel: HomeViewModel, scrollState: ScrollState){
    val context = LocalContext.current
    val name = "\nSettings + 1"
    val home = stringResource(id = R.string.settings)
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    homeViewModel.getUserData()

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
            HomeScreenDrawerHeader(stringResource(id = R.string.settings))
//            HomeScreenDrawerHeader(homeViewModel.emailId.value)
            NavigationDrawerBody(navigationDrawerItems = homeViewModel.navigationItemList,
                onNavigationItemClicked = {
                    when(it.title){
                        "Home" -> {
                            Log.d("Home", "Inside onNavigationItemClicked Home = ${it.itemId}, ${it.title}")
                            navController.navigate(Routes.Home.route)
                        }
                        "Profile" -> {
                            Log.d("Profile ", "Inside onNavigationItemClicked Profile = ${it.itemId}, ${it.title}")
                        }
                        "Setting" -> {
                            Log.d("Setting ", "Inside onNavigationItemClicked Settings = ${it.itemId}, ${it.title}")
                            navController.navigate(Routes.Settings.route)
                        }
                        "Logout" -> {
                            Log.d("Logout", "Inside onNavigationItemClicked Logout = ${it.itemId}, ${it.title}")
                            homeViewModel.logOut(navController = navController)
                        }
                    }
                })
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