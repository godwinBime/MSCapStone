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
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import com.example.data.viewmodel.HomeViewModel
import com.example.data.viewmodel.SignUpPageViewModel
import com.example.data.viewmodel.UpdateProfileViewModel
import com.example.loginpage.R
import com.example.loginpage.ui.component.DividerTextComponent
import com.example.loginpage.ui.component.DrawerContentComponent
import com.example.loginpage.ui.component.GeneralBottomAppBar
import com.example.loginpage.ui.component.HomeScreenDrawerHeader
import com.example.loginpage.ui.component.HomeScreenTopAppBar
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
                updateProfileViewModel: UpdateProfileViewModel = viewModel(),
                googleSignInViewModel: GoogleSignInViewModel = hiltViewModel()
){
    val scrollState = rememberScrollState()
    val googleSignInState = googleSignInViewModel.googleState.value
    Box(modifier = Modifier
        .background(Color.Green)
        .fillMaxSize(),
        contentAlignment = Alignment.Center) {
        ScaffoldUserProfileWithTopBar(navController = navController, scrollState = scrollState)
        if (updateProfileViewModel.displayUserProfileInProgress.value || googleSignInState.loading){
            CircularProgressIndicator()
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldUserProfileWithTopBar(
    navController: NavHostController,
    homeViewModel: HomeViewModel = viewModel(),
    signUpPageViewModel: SignUpPageViewModel = viewModel(),scrollState: ScrollState
){
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
            GeneralBottomAppBar(navController)
        },

        floatingActionButton = {
            FloatingActionButton(onClick = {
                getToast(context, "Add floating button clicked!")
                /*navController.navigate(Routes.SignUp.route)*/
                                           },
                shape = RoundedCornerShape(12.dp),
                //containerColor = Color(0xff344ceb)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        },
        topBar = { HomeScreenTopAppBar(navController, userProfile, action = "UserProfile Screen",
            navigationIconClicked = {
                coroutineScope.launch {
                    scaffoldState.drawerState.open()
                }
            }
        )
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen, /*Gesture is on enabled when drawer is in open state*/
        drawerContent = {
//            HomeScreenDrawerHeader(homeViewModel.emailId.value)
//            HomeScreenDrawerHeader(homeViewModel.fullNames.value)
            DrawerContentComponent(
                navController = navController,
                homeViewModel = homeViewModel,
                headerTitle = stringResource(R.string.profile),
                defaultTitle = 1
            )
        },
        content = {
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
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
//                    .background(Color.Red)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Spacer(modifier = Modifier.height(10.dp))

                if (providerId == "password") {
                    val isEnable = true
                    Log.d(TAG, "ProviderId in UserProfile.kt: email/password")
                    signUpPageViewModel.fetchedUSerData(signUpPageViewModel = signUpPageViewModel)

                    NormalTextComponent(value = "${signUpPageViewModel.fullNames} ")
                    DividerTextComponent()
                    Spacer(modifier = Modifier.height(10.dp))
                    NormalTextComponent(value = "Phone Number: ${signUpPageViewModel.phoneNumber}")
                    Spacer(modifier = Modifier.height(20.dp))
                    NormalTextComponent(value = "Email: ${signUpPageViewModel.userEmail}")

                    Spacer(modifier = Modifier.height(40.dp))
                    SubButton(
                        navController = navController,
                        value = stringResource(R.string.update_profile),
                        rank = 6,
                        isEnable = isEnable,
                        originalPage = "UserProfile.kt"
                    )
                }else if (providerId == "google.com"){
                    Log.d(TAG, "ProviderId in UserProfile.kt: google.com")
                    Spacer(modifier = Modifier.height(10.dp))
                    NormalTextComponent(value = "${FirebaseAuth.getInstance().currentUser?.displayName} ")
                    DividerTextComponent()
                    Spacer(modifier = Modifier.height(10.dp))
                }else{
                    Log.d(TAG, "No provider found")
                    NormalTextComponent(value = "No user found...")
                }
            }
        }
    )

}