package com.example.component

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.data.Constant.ServerClient
import com.example.data.google.GoogleSignInViewModel
import com.example.data.home.HomeViewModel
import com.example.navigation.Routes
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch

@Composable
fun GoogleSignInScreen(
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
                val  credentials = GoogleAuthProvider.getCredential(result.idToken, null)
                googleSignInViewModel.googleSignIn(credentials)
            }catch (it: ApiException){
                print(it)
            }
        }

    val context = LocalContext.current
    Button(
        modifier = Modifier
            .height(60.dp),
        onClick = {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(ServerClient)
                .requestProfile()
                .build()

            val googleSignInClient = GoogleSignIn.getClient(context, gso)
            launcher.launch(googleSignInClient.signInIntent)
        }) {
//        Icon(imageVector = Icons.Filled.Favorite, contentDescription = null)
//        Image(
//            modifier = Modifier
//                .background(Color.Red),
//            painter = painterResource(id = R.drawable.ic_launcher_foreground),
//            contentDescription = null)
        Spacer(modifier = Modifier.width(3.dp))
        Text(text = value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White)

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