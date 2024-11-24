package com.example.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
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
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import com.example.data.viewmodel.HomeViewModel
import com.example.data.viewmodel.ProfileViewModel
import com.example.data.viewmodel.SignUpPageViewModel
import com.example.loginpage.R
import com.example.loginpage.ui.component.DividerTextComponent
import com.example.loginpage.ui.component.DrawerContentComponent
import com.example.loginpage.ui.component.GeneralBottomAppBar
import com.example.loginpage.ui.component.HomeScreenTopAppBar
import com.example.loginpage.ui.component.LoadingScreenComponent
import com.example.loginpage.ui.component.NormalTextComponent
import com.example.loginpage.ui.component.SubButton
import com.example.loginpage.ui.component.getToast
import com.example.navigation.Routes
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@Composable
fun UserProfile(navController: NavHostController,
                homeViewModel: HomeViewModel = viewModel(),
                signUpPageViewModel: SignUpPageViewModel = viewModel(),
                updateProfileViewModel: ProfileViewModel = viewModel(),
                googleSignInViewModel: GoogleSignInViewModel = hiltViewModel()){
    val scrollState = rememberScrollState()
    Box(modifier = Modifier
        .background(Color.Green)
        .fillMaxSize(),
        contentAlignment = Alignment.Center) {
        LoadingScreenComponent(googleSignInViewModel = googleSignInViewModel,
            signUpPageViewModel = signUpPageViewModel)
        ScaffoldUserProfileWithTopBar(navController = navController, scrollState = scrollState)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldUserProfileWithTopBar(
    navController: NavHostController,
    homeViewModel: HomeViewModel = viewModel(),
    signUpPageViewModel: SignUpPageViewModel = viewModel(),scrollState: ScrollState,
    profileViewModel: ProfileViewModel = viewModel()) {
    val context = LocalContext.current
    val TAG = SignUpPageViewModel::class.simpleName
    val userProfile = stringResource(id = R.string.profile)
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val user = FirebaseAuth.getInstance().currentUser
    val providerId = signUpPageViewModel.checkUserProvider(user = user)

    Scaffold(
        modifier = Modifier
            .background(Color.Red),
        scaffoldState = scaffoldState,
        bottomBar = {
            GeneralBottomAppBar(
                navController = navController, providerId = providerId,
                trueIndex = 3)
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    getToast(context, "Add Employee floating button clicked!")
//                    navController.navigate(Routes.AddEmployee.route)
                },
                shape = RoundedCornerShape(12.dp),
                containerColor = Color(0xFF838282)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        },
        topBar = {
            HomeScreenTopAppBar(navController, userProfile, action = "UserProfile Screen",
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
//                headerTitle = stringResource(R.string.profile),
//                defaultTitle = 1
            )
        },
        content = {
            /*
//            Card(
//                modifier = Modifier
//                    .background(Color.Red)
//            ) {
//                Spacer(modifier = Modifier.height(80.dp))
//                NormalTextComponent(value = "Welcome, $name")
//                Spacer(modifier = Modifier.height(80.dp))
//                SubButton(navController = navController,
//                    value = stringResource(R.string.update_profile),
//                    rank = 6,
//                    isEnable = isEnable,
//                    originalPage = "UserProfile.kt"
//                )
//            }
            */
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
//                    .background(Color.Red)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Spacer(modifier = Modifier.height(40.dp))
                when(providerId){
                    "password" -> {
                        NormalTextComponent(value = "Title: " + stringResource(id = R.string.admin_profile))
                        val isEnable = true
                        Log.d(TAG, "ProviderId in UserProfile.kt: email/password...")
                        signUpPageViewModel.fetchedUSerData(signUpPageViewModel = signUpPageViewModel,
                            providerId = "password")
//                        PhotoPickerComponent(navController = navController, size = 90.dp)
                        NormalTextComponent(value = "Full Names: ${signUpPageViewModel.fullNames} ")
                        DividerTextComponent()
                        Spacer(modifier = Modifier.height(10.dp))
                        NormalTextComponent(value = "Phone Number: ${signUpPageViewModel.phoneNumber}")
                        Spacer(modifier = Modifier.height(10.dp))
                        NormalTextComponent(value = "Email: ${signUpPageViewModel.userEmail}")

                        Spacer(modifier = Modifier.height(40.dp))
                        SubButton(
                            navController = navController,
                            value = stringResource(R.string.update_profile),
                            rank = 6,
                            isEnable = isEnable,
                            originalPage = "UserProfile.kt"
                        )
                    }
                    "google.com" -> {
                        Log.d(TAG, "ProviderId in UserProfile.kt: google.com")
//                        PhotoPickerComponent(navController = navController)
//                        GoogleAccountProfilePictureComponent(user = user, size = 120.dp)
                        Spacer(modifier = Modifier.height(20.dp))
                        NormalTextComponent(value = "Full Names: ${user?.displayName}")
                        NormalTextComponent(value = "Email: ${user?.email} ")
                        DividerTextComponent()
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    "None" -> {
                        Log.d(TAG, "No provider found")
                        NormalTextComponent(value = stringResource(id = R.string.no_user_found))
                    }
                }
            }
        }
    )
}