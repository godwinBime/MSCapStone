package com.example.screen

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import android.annotation.SuppressLint
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Card
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Scaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.data.viewmodel.HomeViewModel
import com.example.data.viewmodel.SignUpPageViewModel
import com.example.loginpage.R
import com.example.loginpage.ui.component.DesignMFASpace
import com.example.loginpage.ui.component.HeadingTextComponent
import com.example.loginpage.ui.component.TopAppBarBeforeLogin

@Composable
fun ChooseVerificationMethod(navController: NavHostController,
                             signUpPageViewModel: SignUpPageViewModel,
                             homeViewModel: HomeViewModel){
    val scrollState = rememberScrollState()
    Box(modifier = Modifier.fillMaxSize()){
        ScaffoldChooseVerificationMethod(navController = navController, scrollState,
            signUpPageViewModel, homeViewModel)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldChooseVerificationMethod(navController: NavHostController,
                                     scrollState: ScrollState,
                                     signUpPageViewModel: SignUpPageViewModel,
                                     homeViewModel: HomeViewModel){
    val context = LocalContext.current
    Scaffold(
        topBar = { TopAppBarBeforeLogin(navController, "MFA",
            true, action = "Choose Verification Method then proceed.",
            homeViewModel, screenName = "ChooseVerificationMethod") },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .verticalScroll(scrollState)
                    .padding(15.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                HeadingTextComponent(value = "Choose Verification Method")

                Spacer(modifier = Modifier.height(20.dp))
                DesignMFASpace(navController = navController,
                    value = "Authenticator App", buttonType = "Enter Code",
                    type = "MFAAuthenticatorApp",
                    signUpPageViewModel = signUpPageViewModel)

                Spacer(modifier = Modifier.height(30.dp))

                DesignMFASpace(navController = navController, value = "SMS Verification",
                    buttonType = "Send Text", type = "MFASMSVerification",
                    signUpPageViewModel = signUpPageViewModel)

                Spacer(modifier = Modifier.height(40.dp))

                DesignMFASpace(navController = navController, value = "Email Verification",
                    buttonType = "Send Email", type = "MFAVerifyEmail",
                    signUpPageViewModel = signUpPageViewModel)

                Spacer(modifier = Modifier
                    .height(90.dp))

                Button(
                    onClick = {
                       homeViewModel.logOut(navController = navController,
                           signUpPageViewModel = signUpPageViewModel,
                           context = context)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    )
                ) {
                    Box(modifier = Modifier
                        .heightIn(50.dp)
                        .width(140.dp)
                        .background(
                            brush = Brush.horizontalGradient(listOf(Color.Gray, Color.Black, Color.Gray)),
                            shape = RoundedCornerShape(95.dp)
                        ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.cancel),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
                Card(modifier = Modifier
//                    .height(90.dp)
                    .fillMaxWidth()) {
                 }
            }
        }
    )
}
