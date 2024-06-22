package com.example.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.component.CustomTopAppBar
import com.example.component.NormalTextComponent
import com.example.data.SignUpPageViewModel
import com.example.loginpage.R

@Composable
fun Home(navController: NavHostController, signUpPageViewModel: SignUpPageViewModel){
    val scrollState = rememberScrollState()
    Box(modifier = Modifier.fillMaxSize()){
        ScaffoldHomeWithTopBar(navController, signUpPageViewModel, scrollState)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldHomeWithTopBar(navController: NavHostController,
                           signUpPageViewModel: SignUpPageViewModel, scrollState: ScrollState){
    val context = LocalContext.current
    val name = "User + 1"
    val home = stringResource(id = R.string.home)

    Scaffold(
        bottomBar = {
            GeneralBottomAppBar(navController)
        },

        floatingActionButton = {
            FloatingActionButton(onClick = { getToast(context, "Add floating button clicked!") },
                shape = RoundedCornerShape(12.dp),
                //containerColor = Color(0xff344ceb)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        },
        topBar = { CustomTopAppBar(navController, home, true,
            logoutButtonClicked = {
                signUpPageViewModel.logOut(navController = navController)
            }
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
                Spacer(modifier = Modifier.height(80.dp))
                NormalTextComponent(value = "Welcome, $name")

                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    )
}