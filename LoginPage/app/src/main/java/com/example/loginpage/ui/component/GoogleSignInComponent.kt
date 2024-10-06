package com.example.loginpage.ui.component

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.example.navigation.Routes
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch

@Composable
fun GoogleSignInScreen(
    signUpPageViewModel: SignUpPageViewModel = viewModel(),
    googleSignInViewModel: GoogleSignInViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel,
    value: String, navController: NavHostController){
    val googleSignInState = googleSignInViewModel.googleState.value
    val scope = rememberCoroutineScope()

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()){
            val account = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val result = account.getResult(ApiException::class.java)
//                handleSignInResult(result)
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
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(SERVERCLIENT)
                .requestProfile()
                .build()

            val googleSignInClient = GoogleSignIn.getClient(context, gso)
//            val googleSignInIntent = googleSignInClient.signInIntent
            launcher.launch(
                googleSignInClient.signInIntent
            )
        }
    ) {
        Spacer(modifier = Modifier.width(3.dp))
//        Image(
//            painter = painterResource(id = R.mipmap.ic_google),
//            contentDescription = null,
//            modifier = Modifier.size(40.dp)
//        )
        Text(text = value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Blue)

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

@Composable
fun HandleSignInResult(task: GoogleSignInAccount){
    Column {
//        Image(
//            painter = rememberImagePainter(task.photoUrl),
//            contentDescription = "Profile Picture"
//        )
    }
}