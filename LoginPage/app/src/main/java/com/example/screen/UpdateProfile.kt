package com.example.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.loginpage.R
import com.example.loginpage.ui.theme.LoginPageTheme

class UpdateProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginPageTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // TODO: Undecided
                }
            }
        }
    }
}
@Composable
fun UpdateProfile(navController: NavHostController){
    Box(modifier = Modifier.fillMaxSize()){
        ScaffoldUpdateProfileTopBar(navController = navController)
    }
}

@Composable
fun ScaffoldUpdateProfileTopBar(navController: NavHostController){
    var userName by rememberSaveable { mutableStateOf("") }
    val lastName = stringResource(id = R.string.last_name)
    val email = stringResource(id = R.string.email)
    val phoneNumber = stringResource(id = R.string.phone_number)
    val oldPassword = stringResource(id = R.string.old_password)
    val newPassword = stringResource(id = R.string.new_password)
    val confirmNewPassword = stringResource(id = R.string.confirm_new_password)
}