package com.example.loginpage.ui.component

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.data.uistate.EmailVerifyUIState
import com.example.data.uistate.NavItem
import com.example.data.viewmodel.HomeViewModel
import com.example.data.viewmodel.SignUpPageViewModel
import com.example.data.viewmodel.VerifyEmailViewModel
import com.example.loginpage.R
import com.example.navigation.Routes
import com.example.screen.Home
import com.example.screen.Settings
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

private val TAG = VerifyEmailViewModel::class.simpleName

@Composable
fun GeneralBottomAppBar(
    navController: NavHostController,
    signUpPageViewModel: SignUpPageViewModel = viewModel(),
    verifyEmailViewModel: VerifyEmailViewModel = viewModel(),
    homeViewModel: HomeViewModel = viewModel(),
    providerId: String){
    val context = LocalContext.current.applicationContext
    val navItemList = listOf(
        NavItem(label = "Home", icon = Icons.Default.Home),
        NavItem(label = "Edit", icon = Icons.Default.Edit),
        NavItem(label = "Delete", icon = Icons.Default.Delete),
        NavItem(label = "Profile", icon = Icons.Default.Person),
        NavItem(label = "Settings", icon = Icons.Default.Settings)
    )
    var selectedIndex by remember { mutableIntStateOf(0) }
    NavigationBar {
        navItemList.forEachIndexed{index, navItem ->
            NavigationRailItem(
                selected = selectedIndex == index,
                onClick = {
                    bottomNavbarContentScreen(navController = navController,
                        signUpPageViewModel = signUpPageViewModel,
                        verifyEmailViewModel = verifyEmailViewModel,
                        selectedIndex = index,
                        providerId = providerId,
                        context = context)
                    selectedIndex = index
                },
                icon = {
                    Icon(imageVector = navItem.icon, contentDescription = "Icon")
                },
                label = {
                    Text(text = navItem.label)
                }
            )
        }
    }
}

fun bottomNavbarContentScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    signUpPageViewModel: SignUpPageViewModel,
    verifyEmailViewModel: VerifyEmailViewModel,
    selectedIndex: Int, providerId: String, context: Context){
    when(selectedIndex){
        0 -> {
            getToast(context, action = "Home Nav button clicked!")
            navController.navigate(Routes.Home.route)
        }
        1 -> {
            if (providerId == "password") {
                navController.navigate(Routes.UserProfile.route)
            }else if (providerId == "google.com"){
                getToast(context = context, "Use Your Google Account for this action.")
            }
        }
        2 -> {
            val auth = FirebaseAuth.getInstance()
            getToast(context, action = "Delete Bottom Nav button clicked!")
            val email = auth.currentUser?.email?.let { userEmail ->
                EmailVerifyUIState(
                    userEmail
                )
            }
            if (email != null) {
                verifyEmailViewModel.sendOTPToEmail(
                    email = email,
                    navController = navController,
                    type = "DeleteProfile")
                if (verifyEmailViewModel.isOTPSent) {
                    Log.d(TAG, "OTPSent...Navigating to verify OTP and DeleteProfile...")
                    navController.navigate(
                        Routes.MFAVerifyEmail.route)
                }
            }else {
                getToast(context = context, "No email provided.")
            }
        }
        3 -> {
            getToast(context, action = "Profiles Nav button clicked!")
            navController.navigate(Routes.UserProfile.route)
        }
        4 -> {
            getToast(context, action = "Setting Nav button clicked!")
            navController.navigate(Routes.Settings.route)
        }
    }
}

fun getToast(context: Context, action: String, toastDuration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(context, action, toastDuration).show()
}

/*
@Composable
fun GeneralBottomAppBar(
    navController: NavHostController,
    signUpPageViewModel: SignUpPageViewModel = viewModel(),
    verifyEmailViewModel: VerifyEmailViewModel = viewModel(),
    homeViewModel: HomeViewModel = viewModel()){
    val scaffoldState = rememberScaffoldState()
    var selectedIndex by remember { mutableIntStateOf(0) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Titles
    val home = stringResource(id = R.string.home)
    val userProfile = stringResource(id = R.string.profile)
    val updateProfileTitle = stringResource(id = R.string.update_profile)

    val user = FirebaseAuth.getInstance().currentUser
    val providerId = signUpPageViewModel.checkUserProvider(user = user)

    if (providerId == "password") {
        signUpPageViewModel.fetchedUSerData(signUpPageViewModel = signUpPageViewModel,
            userType = "password")
    }

    val navItemList = listOf(
        NavItem(label = "Home", icon = Icons.Default.Home),
        NavItem(label = "Edit", icon = Icons.Default.Edit),
        NavItem(label = "Delete", icon = Icons.Default.Delete),
        NavItem(label = "Profile", icon = Icons.Default.Person),
        NavItem(label = "Settings", icon = Icons.Default.Settings)
    )
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                navItemList.forEachIndexed{index, navItem ->
                    NavigationRailItem(
                        selected = selectedIndex == index,
                        onClick = {
                            selectedIndex = index
                        },
                        icon = {
                            Icon(imageVector = navItem.icon, contentDescription = "Icon")
                        },
                        label = {
                            Text(text = navItem.label)
                        }
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { getToast(context, "Add floating button clicked!") },
                shape = RoundedCornerShape(12.dp),
                //containerColor = Color(0xff344ceb)
            ) {
                androidx.compose.material.Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        },

        topBar = {
            if (selectedIndex == 1){
                CustomTopAppBar(navController, title = updateProfileTitle, true,
                    logoutButtonClicked = {
                        homeViewModel.logOut(navController = navController)
                    }
                )
            }else {
                HomeScreenTopAppBar(navController, title = home, action = "Home Screen",
                    navigationIconClicked = {
                        coroutineScope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }
                )
            }
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen, /*Gesture is enabled when drawer is in open state*/

        drawerContent = {
//            HomeScreenDrawerHeader(homeViewModel.emailId.value)
//            HomeScreenDrawerHeader(value = name)
            DrawerContentComponent(
                navController = navController,
                homeViewModel = homeViewModel,
                headerTitle = stringResource(id = R.string.home),
                defaultTitle = 1
            )
        }
    ) { innerPadding ->
        BottomNavbarContentScreen(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            providerId = providerId,
            selectedIndex = selectedIndex
        )
    }

}

@Composable
fun BottomNavbarContentScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    signUpPageViewModel: SignUpPageViewModel = viewModel(),
    verifyEmailViewModel: VerifyEmailViewModel = viewModel(),
    providerId: String, selectedIndex: Int){
    when(selectedIndex){
        0 -> {
//            navController.navigate(Routes.Home.route)
            Home(navController = navController)
        }
        1 -> {
            navController.navigate(Routes.UpdateProfile.route)
        }
        2 -> {
            navController.navigate(Routes.DeleteProfile.route)
        }
        3 -> {
            navController.navigate(Routes.UserProfile.route)
        }
        4 -> {
            navController.navigate(Routes.Settings.route)
        }
    }
}

 */
