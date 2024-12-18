package com.example.loginpage.ui.component

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.data.local.entities.Constant.SERVERCLIENT
import com.example.data.viewmodel.GoogleSignInViewModel
import com.example.data.viewmodel.HomeViewModel
import com.example.data.viewmodel.SignUpPageViewModel
import com.example.data.viewmodel.TimerViewModel
import com.example.loginpage.R
import com.example.navigation.Routes
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch

private val TAG = TimerViewModel::class.simpleName
@Composable
fun GoogleSignInScreen(
    signUpPageViewModel: SignUpPageViewModel = viewModel(),
    googleSignInViewModel: GoogleSignInViewModel = hiltViewModel(),
    timerViewModel: TimerViewModel = viewModel(),
    homeViewModel: HomeViewModel = viewModel(),
    value: String, navController: NavHostController){
    val googleSignInState = googleSignInViewModel.googleState.value
    val scope = rememberCoroutineScope()
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()){
            val account = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val result = account.getResult(ApiException::class.java)
                val  credentials = GoogleAuthProvider.getCredential(result.idToken, null)
                googleSignInViewModel.googleSignIn(
                    credential =  credentials,
                    signUpPageViewModel = signUpPageViewModel)
            }catch (it: ApiException){
                print(it)
            }
        }
    val context = LocalContext.current
    OutlinedButton (
        modifier = Modifier
            .height(60.dp),
        onClick = {
            if(!timerViewModel.isUserTyping(context = context)) {
                val startTime = System.currentTimeMillis()
                timerViewModel.saveAuthStartTime(context = context, startTime = startTime)
                Log.d(TAG, "startTime inside GoogleSignInScreen()...$startTime")
            }else{
                Log.d(TAG, "User typing already before clicking Google button...")
            }
            val gso = homeViewModel.googleSignInOptions()
            val googleSignInClient = GoogleSignIn.getClient(context, gso)
            launcher.launch(
                googleSignInClient.signInIntent
            )
        }
    ) {
        Image(
            modifier = Modifier
                .size(24.dp),
            painter = painterResource(R.drawable.google),
            contentDescription = "Google"
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black)

        LaunchedEffect(key1 = googleSignInState.success) {
            scope.launch {
                if(googleSignInState.success != null){
                    getToast(context, "Google Sign In Success",
                        Toast.LENGTH_LONG)
                    navController.navigate(Routes.Home.route)
                }
            }
        }

        LaunchedEffect(key1 = googleSignInState.error) {
            scope.launch {
                if(googleSignInState.error.isNotEmpty()){
                    getToast(context, "Google Sign in Error",
                        Toast.LENGTH_LONG)
                }
            }
        }
    }
}
