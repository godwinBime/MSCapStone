package com.example.loginpage.ui.component

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.data.uistate.EmailVerifyUIState
import com.example.data.uistate.NavItem
import com.example.data.viewmodel.HomeViewModel
import com.example.data.viewmodel.SignUpPageViewModel
import com.example.data.viewmodel.VerifyEmailViewModel
import com.example.navigation.Routes
import com.google.firebase.auth.FirebaseAuth

private val TAG = VerifyEmailViewModel::class.simpleName

@Composable
fun GeneralBottomAppBar(
    navController: NavHostController,
    signUpPageViewModel: SignUpPageViewModel = viewModel(),
    verifyEmailViewModel: VerifyEmailViewModel = viewModel(),
    homeViewModel: HomeViewModel = viewModel(),
    providerId: String, pageType: String = "Home", trueIndex: Int){
    var selectedIndex by rememberSaveable { mutableIntStateOf(trueIndex) }
    val context = LocalContext.current.applicationContext
    val navItemList = listOf(
        NavItem(label = "Home", icon = Icons.Default.Home),
        NavItem(label = "Edit", icon = Icons.Default.Edit),
        NavItem(label = "Delete", icon = Icons.Default.Delete),
        NavItem(label = "Profiles", icon = Icons.Default.Person),
        NavItem(label = "Settings", icon = Icons.Default.Settings)
    )
    NavigationBar {
        navItemList.forEachIndexed { index, navItem ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = {
                    selectedIndex = index
                    bottomNavbarContentScreen(
                        navController = navController,
                        signUpPageViewModel = signUpPageViewModel,
                        verifyEmailViewModel = verifyEmailViewModel,
                        selectedIndex = index,
                        providerId = providerId,
                        context = context)
                },
                icon = {
                    /*
                    Box(modifier = Modifier
                        .shadow(
                            elevation  = if (selectedIndex == index) 8.dp else 0.dp,
                            shape = MaterialTheme.shapes.small,
                            spotColor = if (selectedIndex == index) Color.Gray else Color.Transparent
                        )) {
                        Icon(
                            imageVector = navItem.icon,
                            contentDescription = "Icon",
                            tint = if (selectedIndex == index) Color.Blue else Color.Black
                        )
                    }*/
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = "Icon",
                        tint = if (selectedIndex == index) Color.Blue else Color.Black
                    )
                },
                label = {
                    Text(text = navItem.label, color = Color.Black)
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
    Log.d(TAG, "selectedIndex in bottomNavbarContentScreen(): $selectedIndex...")

    when(selectedIndex){
        0 -> {
            getToast(context, action = "Home Nav button clicked!")
            Log.d(TAG, "Navigating to Home...")
            navController.navigate(Routes.Home.route)
        }
        1 -> {
            if (providerId == "password") {
                Log.d(TAG, "Navigating to Edit...")
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
                    navController.navigate(Routes.MFAVerifyEmail.route)
                }
            }else {
                getToast(context = context, "No email provided.")
            }
        }
        3 -> {
            getToast(context, action = "Profiles Nav button clicked!")
            Log.d(TAG, "Navigating to UserProfile...")
            navController.navigate(Routes.UserProfile.route)
        }
        4 -> {
            getToast(context, action = "Setting Nav button clicked!")
            Log.d(TAG, "Navigating to Settings...")
            navController.navigate(Routes.Settings.route)
        }
    }
}

fun getToast(context: Context, action: String, toastDuration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(context, action, toastDuration).show()
}

