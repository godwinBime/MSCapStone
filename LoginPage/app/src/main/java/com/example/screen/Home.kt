package com.example.screen

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Scaffold
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.data.viewmodel.GoogleSignInViewModel
import com.example.data.viewmodel.HomeViewModel
import com.example.data.viewmodel.SignUpPageViewModel
import com.example.data.viewmodel.TimerViewModel
import com.example.data.viewmodel.VerifyEmailViewModel
import com.example.loginpage.R
import com.example.loginpage.ui.component.DrawerContentComponent
import com.example.loginpage.ui.component.GeneralBottomAppBar
import com.example.loginpage.ui.component.HomeScreenTopAppBar
import com.example.loginpage.ui.component.LoadingScreenComponent
import com.example.loginpage.ui.component.NormalTextComponent
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@Composable
fun Home(navController: NavHostController,
         homeViewModel: HomeViewModel = viewModel(),
         googleSignInViewModel: GoogleSignInViewModel = hiltViewModel(),
         signUpPageViewModel: SignUpPageViewModel = viewModel()){
    val scrollState = rememberScrollState()
    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center){
        ScaffoldHomeScreenWithTopBar(navController, homeViewModel, scrollState)
        LoadingScreenComponent(googleSignInViewModel = googleSignInViewModel,
            signUpPageViewModel = signUpPageViewModel)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldHomeScreenWithTopBar(navController: NavHostController,
                                 homeViewModel: HomeViewModel = viewModel(),
                                 scrollState: ScrollState,
                                 verifyEmailViewModel: VerifyEmailViewModel = viewModel(),
                                 timerViewModel: TimerViewModel = viewModel(),
                                 signUpPageViewModel: SignUpPageViewModel = viewModel()){
    val context = LocalContext.current
    val home = stringResource(id = R.string.home)
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val user = FirebaseAuth.getInstance().currentUser
    val providerId = signUpPageViewModel.checkUserProvider(user = user)
    val authStartTime = timerViewModel.getAuthStartTime(context = context)
    val isAuthTimeRecorded = timerViewModel.isAuthTimeRecorded(context = context)
    val authEndTime = if (!isAuthTimeRecorded) {
        val endTime = System.currentTimeMillis()
        timerViewModel.saveAuthEndTime(context = context, endTime = endTime)
        timerViewModel.setAuthTimeRecorded(context = context)
        endTime
    }else{
        timerViewModel.getAuthEndTime(context = context)
    }
    val timerResults = timerViewModel.calculateAuthDuration(startTime = authStartTime,
        endTime = authEndTime, context = context)
    val authDuration = rememberSaveable { mutableStateOf("")}

//    LaunchedEffect(providerId) {
//        if (providerId == "password") {
////        timerViewModel.resetUserTypingFlag(context = context)
//            signUpPageViewModel.fetchedUSerData(
//                signUpPageViewModel = signUpPageViewModel,
//                providerId = "password", context = context
//            )
//        }
//    }

    val fullNames = signUpPageViewModel.getFullNames(context = context)

    if (timerViewModel.isTimerFinished() || timerViewModel.isMfaCounterFinished()){
        LaunchedEffect(Unit) {
            verifyEmailViewModel.resetOtpCode()
            verifyEmailViewModel.resetOTPCode(context = context)
            timerViewModel.resetTimer()
            timerViewModel.mfaResetTimer()
        }
    }
    // Authentication process is complete, set the complete flag to true
    LaunchedEffect(Unit) {
        timerViewModel.setAuthComplete(context = context)
        verifyEmailViewModel.resetOTPCode(context = context)
    }

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            GeneralBottomAppBar(
                navController = navController, providerId = providerId,
                trueIndex = 0)
        },
        topBar = {
            HomeScreenTopAppBar(navController = navController,
                title = home,
                action = "Home Screen",
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
                homeViewModel = homeViewModel
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
                LaunchedEffect(fullNames) {

                }
                Spacer(modifier = Modifier.height(80.dp))
                if (providerId == "password") {
//                    PhotoPickerComponent(navController = navController)
                    NormalTextComponent(
                        value = "Welcome, ${fullNames?.substringBefore(delimiter = " ")}"
                    )
                }else if (providerId == "google.com"){
              /*      PhotoPickerComponent(navController = navController)
                    Spacer(modifier = Modifier.height(80.dp))
                    GoogleAccountProfilePictureComponent(user = user, size = 120.dp)
                    */
                    NormalTextComponent(
                        value = "Welcome, ${user?.displayName?.substringBefore(delimiter = " ")}"
                    )
                }else{
                    NormalTextComponent(value = stringResource(id = R.string.no_user_found))
                }
                Spacer(modifier = Modifier.height(80.dp))
                LaunchedEffect(Unit) {
                    authDuration.value = timerResults
                    if (!isAuthTimeRecorded) {
                        authDuration.value = timerResults
                    }
                }
                Box(modifier = Modifier
                    .shadow(8.dp, RoundedCornerShape(25.dp))
                    .background(Color.LightGray)
                    .size(350.dp, 100.dp)
                    .border(2.dp, Color.LightGray)
                    .padding(15.dp)) {
                    when (providerId) {
                        "google.com" -> {
                            Image(
                                modifier = Modifier
                                    .size(20.dp),
                                painter = painterResource(R.drawable.google),
                                contentDescription = "Google"
                            )
                        }
                        "password" -> {
                            Image(
                                modifier = Modifier
                                    .size(20.dp),
                                painter = painterResource(R.drawable.email),
                                contentDescription = "Google"
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(40.dp))
                    Text(
                        text = authDuration.value,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(top = 20.dp)
                            .fillMaxWidth(),
                        style = TextStyle(fontSize = 17.sp)
                    )
                }
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    )
}