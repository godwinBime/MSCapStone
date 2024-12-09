package com.example.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.data.viewmodel.GoogleSignInViewModel
import com.example.data.viewmodel.HomeViewModel
import com.example.data.viewmodel.SignUpPageViewModel
import com.example.data.viewmodel.ProfileViewModel
import com.example.data.viewmodel.TimerViewModel
import com.example.data.viewmodel.VerifyEmailViewModel
import com.example.loginpage.R
import com.example.loginpage.ui.component.DividerTextComponent
import com.example.loginpage.ui.component.DrawerContentComponent
import com.example.loginpage.ui.component.GeneralBottomAppBar
import com.example.loginpage.ui.component.HomeScreenTopAppBar
import com.example.loginpage.ui.component.LoadingScreenComponent
import com.example.loginpage.ui.component.NormalTextComponent
import com.example.loginpage.ui.component.SubButton
import com.example.loginpage.ui.component.getToast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch


@Composable
fun DeleteProfile(navController: NavHostController,
                  homeViewModel: HomeViewModel = viewModel(),
                  signUpPageViewModel: SignUpPageViewModel = viewModel(),
                  googleSignInViewModel: GoogleSignInViewModel = hiltViewModel()){
    val scrollState = rememberScrollState()
    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center){
        ScaffoldDeleteProfileWithTopBar(navController = navController,
            scrollState = scrollState)
        LoadingScreenComponent(googleSignInViewModel = googleSignInViewModel,
            signUpPageViewModel = signUpPageViewModel)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldDeleteProfileWithTopBar(navController: NavHostController,
                                    homeViewModel: HomeViewModel = viewModel(),
                                    scrollState: ScrollState,
                                    verifyEmailViewModel: VerifyEmailViewModel = viewModel(),
                                    timerViewModel: TimerViewModel = viewModel(),
                                    profileViewModel: ProfileViewModel = viewModel(),
                                    signUpPageViewModel: SignUpPageViewModel = viewModel()) {

    val context = LocalContext.current
    val warning = "\n${stringResource(R.string.delete_warning)}"
    val deleteTitle = stringResource(id = R.string.delete_profile)
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val user = FirebaseAuth.getInstance().currentUser
    val providerId = signUpPageViewModel.checkUserProvider(user = user)

    val fullNames = signUpPageViewModel.getFullNames(context = context)
    val userEmail = signUpPageViewModel.getUserEmail(context = context)
    val userPhoneNumber = signUpPageViewModel.getUserPhoneNumber(context = context)

//    if (providerId == "password") {
//        signUpPageViewModel.fetchedUSerData(signUpPageViewModel = signUpPageViewModel,
//            providerId = "password", context = context)
//    }

    if (timerViewModel.isMfaCounterFinished() || timerViewModel.isTimerFinished()) {
        LaunchedEffect(Unit) {
            verifyEmailViewModel.resetOtpCode()
            timerViewModel.resetTimer()
            timerViewModel.mfaResetTimer()
        }
    }
/*
    GeneralBottomAppBar(navController = navController, providerId = providerId)
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
        when(providerId) {
            "password" -> {
                NormalTextComponent(
                    value =
                    "Full Names: ${signUpPageViewModel.fullNames} "
                )
                Spacer(modifier = Modifier.height(10.dp))
                NormalTextComponent(
                    value =
                    "Phone Number: ${signUpPageViewModel.phoneNumber}"
                )
                Spacer(modifier = Modifier.height(20.dp))
                NormalTextComponent(value = "Email: ${signUpPageViewModel.userEmail}")
            }
            "google.com" -> {
                Spacer(modifier = Modifier.height(20.dp))
                Image(
                    painter = rememberAsyncImagePainter(
                        model = user?.photoUrl,
                    ),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .clip(CircleShape)
//                            .padding(2.dp)
                        .size(120.dp),
                    contentScale = ContentScale.Crop
                )
                NormalTextComponent(
                    value =
                    "Full Names: ${user?.displayName} "
                )
                Spacer(modifier = Modifier.height(20.dp))
                NormalTextComponent(value = "Email: ${user?.email}")
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
        SubButton(
            navController = navController,
            value = stringResource(R.string.delete_profile),
            rank = 9,
            isEnable = true,
            originalPage = "DeleteProfile.kt",
            userType = providerId
        )
        Spacer(modifier = Modifier.height(80.dp))
    }
    */

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            GeneralBottomAppBar(navController = navController, providerId = providerId,
                trueIndex = 2)
        },
/*
        floatingActionButton = {
            FloatingActionButton(
                onClick = { getToast(context, "Add floating button clicked!") },
                shape = RoundedCornerShape(12.dp),
                containerColor = Color(0xFFE1B0B0)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        },*/
        topBar = {
            HomeScreenTopAppBar(navController, title = deleteTitle, action = "Caution: Delete Profile.",
                navigationIconClicked = {
                    coroutineScope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            )
        },
        /*
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen, /*Gesture is on enabled when drawer is in open state*/
        drawerContent = {
            DrawerContentComponent(
                navController = navController,
                homeViewModel = homeViewModel,
//                headerTitle = stringResource(id = R.string.delete_profile)
            )
        },*/
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
                when(providerId) {
                    "password" -> {
                        NormalTextComponent(
                            value =
                            "Full Names: $fullNames "
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        NormalTextComponent(
                            value =
                            "Phone Number: $userPhoneNumber"
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        NormalTextComponent(value = "Email: $userEmail")
                    }
                    "google.com" -> {
                        Spacer(modifier = Modifier.height(20.dp))
                        Image(
                            painter = rememberAsyncImagePainter(
                                model = user?.photoUrl,
                            ),
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .clip(CircleShape)
//                            .padding(2.dp)
                                .size(120.dp),
                            contentScale = ContentScale.Crop
                        )
                        NormalTextComponent(
                            value =
                            "Full Names: ${user?.displayName} "
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        NormalTextComponent(value = "Email: ${user?.email}")
                    }
                }
                Spacer(modifier = Modifier.height(40.dp))

                SubButton(
                    navController = navController,
                    value = stringResource(R.string.delete_profile),
                    rank = 9,
                    isEnable = true,
                    originalPage = "DeleteProfile.kt",
                    userType = providerId
                )
                Spacer(modifier = Modifier.height(40.dp))
                Text(text = profileViewModel.message,
                    color = Color.Red,
                    modifier = Modifier.padding(12.dp))
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    )
}
