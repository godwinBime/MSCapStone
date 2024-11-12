package com.example.screen

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.data.viewmodel.GoogleSignInViewModel
import com.example.data.viewmodel.HomeViewModel
import com.example.data.viewmodel.ProfileViewModel
import com.example.data.viewmodel.SignUpPageViewModel
import com.example.loginpage.R
import com.example.loginpage.ui.component.CustomTopAppBar
import com.example.loginpage.ui.component.DrawerContentComponent
import com.example.loginpage.ui.component.GeneralBottomAppBar
import com.example.loginpage.ui.component.HomeScreenTopAppBar
import com.example.loginpage.ui.component.LoadingScreenComponent
import com.example.loginpage.ui.component.NormalTextComponent
import com.example.loginpage.ui.component.PhotoPickerComponent
import com.example.loginpage.ui.component.ProfileButtonComponent
import com.example.loginpage.ui.component.getToast
import com.example.navigation.Routes
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@Composable
fun UserProfilePicture(navController: NavHostController,
                homeViewModel: HomeViewModel = viewModel(),
                signUpPageViewModel: SignUpPageViewModel = viewModel(),
                updateProfileViewModel: ProfileViewModel = viewModel(),
                googleSignInViewModel: GoogleSignInViewModel = hiltViewModel()){
    val scrollState = rememberScrollState()
    Box(modifier = Modifier
        .background(Color.Green)
        .fillMaxSize(),
        contentAlignment = Alignment.Center) {
        ScaffoldUserProfilePictureWithTopBar(navController = navController, scrollState = scrollState)
        LoadingScreenComponent(googleSignInViewModel = googleSignInViewModel)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldUserProfilePictureWithTopBar(
    navController: NavHostController,
    homeViewModel: HomeViewModel = viewModel(),
    signUpPageViewModel: SignUpPageViewModel = viewModel(),scrollState: ScrollState,
    profileViewModel: ProfileViewModel = viewModel()) {
    val userProfile = stringResource(id = R.string.profile_picture)
    val scaffoldState = rememberScaffoldState()
//    val coroutineScope = rememberCoroutineScope()
//    val user = FirebaseAuth.getInstance().currentUser
//    val providerId = signUpPageViewModel.checkUserProvider(user = user)
    val context = LocalContext.current
//    val uploadProgress by profileViewModel.uploadProgress.observeAsState(0f)

    Scaffold(
        modifier = Modifier
            .background(Color.Red),
        scaffoldState = scaffoldState,
        /*
        bottomBar = {
            GeneralBottomAppBar(
                navController = navController, providerId = providerId)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    getToast(context, "Add floating button clicked!")
                    /*navController.navigate(Routes.SignUp.route)*/
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
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen, /*Gesture is on enabled when drawer is in open state*/
        drawerContent = {
            DrawerContentComponent(
                navController = navController,
                homeViewModel = homeViewModel
            )
        },*/
        topBar = {
            CustomTopAppBar(navController = navController, title = userProfile,
                showBackIcon = true) {
                homeViewModel.logOut(navController = navController,
                    signUpPageViewModel = signUpPageViewModel, context = context)
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
//                LinearProgressIndicator(progress = uploadProgress / 100f)
                Spacer(modifier = Modifier.height(50.dp))
                NormalTextComponent(value = stringResource(id = R.string.choose_profile_picture))
                Spacer(modifier = Modifier.height(40.dp))
                PhotoPickerComponent(navController = navController,
                    isImageClicked = true,
                    pageSource = "UserProfilePicture",
                    imageSize = 350.dp,
                    boxSize = 350.dp)
            }
        }
    )
}