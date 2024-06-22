package com.example.component

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController
import com.example.loginpage.R

@Composable
fun CustomTopAppBar(navController: NavHostController,
                    title: String, showBackIcon: Boolean,
                    logoutButtonClicked: () -> Unit){
    TopAppBar(
        backgroundColor = Color.LightGray,
        title = { Text(
            textAlign = TextAlign.Center,
            text = title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis)},
        navigationIcon = if (showBackIcon && navController.previousBackStackEntry != null){
                {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back")
                    }
                }
            }else{
                null
            },
        actions = {
            IconButton(onClick = {
                logoutButtonClicked()
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Logout,
                    contentDescription = stringResource(id = R.string.sign_out),
                    tint = Color.Black)
            }
        }
    )
}
