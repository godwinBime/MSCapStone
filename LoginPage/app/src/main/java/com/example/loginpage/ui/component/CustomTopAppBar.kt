package com.example.loginpage.ui.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.data.local.entities.NavigationItem
import com.example.data.viewmodel.HomeViewModel
import com.example.loginpage.R
import com.example.navigation.Routes

private val TAG = HomeViewModel::class.simpleName
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
            }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Logout,
                    contentDescription = stringResource(id = R.string.sign_out),
                    tint = Color.Black)
            }
        }
    )
}

@Composable
fun TopAppBarBeforeLogin(navController: NavHostController, title: String,
                         showBackIcon: Boolean, action: String, homeViewModel: HomeViewModel,
                         screenName: String = "DefaultScreen"){
    val context = LocalContext.current
    TopAppBar(
        backgroundColor = Color.LightGray,
        title = { Text(
            textAlign = TextAlign.Center,
            text = title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis)},
        navigationIcon = if (showBackIcon && navController.previousBackStackEntry != null){
            {
                IconButton(onClick = {
                    when(screenName){
                        "ChooseVerificationMethod" -> {
                            // Check if user is logged-in and log them out
                            homeViewModel.checkForActiveSession()
                            if(homeViewModel.isUserLoggedIn.value == true){
                                Log.d(TAG, "User was logged-in...logging user out...")
                                homeViewModel.logOut(navController = navController)
                                navController.navigate(Routes.Login.route)
                                getToast(context, "Please Login to continue.")
                            }
                        }
                        "DefaultScreen" -> {
                            navController.navigateUp()
                        }
                    }
                }) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null)
                }
            }
        }else{
            /**
             * TODO: Make sure if the user clicks the back button
             * right to the login page after clicking the logout button,
             * they have to log back in to continue.
             */
            null
        },
        actions = {
            IconButton(onClick = {
                getToast(context = context, action)
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Help,
                    contentDescription = stringResource(id = R.string.help),
                    tint = Color.Black)
            }
        }
    )
}

@Composable
fun HomeScreenTopAppBar(navController: NavHostController, title: String,
                         action: String, navigationIconClicked: () -> Unit){
    val context = LocalContext.current
    TopAppBar(
        backgroundColor = Color.LightGray,
        title = { Text(
            textAlign = TextAlign.Center,
            text = title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis)},
        navigationIcon = {
                IconButton(onClick = { 
                    navigationIconClicked.invoke()
                }) {
                    Icon(imageVector = Icons.Filled.Menu,
                        contentDescription = stringResource(id = R.string.home),
                        tint = Color.Black)
                }
        },
        actions = {
            IconButton(onClick = {
                getToast(context = context, action)
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Help,
                    contentDescription = stringResource(id = R.string.help),
                    tint = Color.Black)
            }
        }
    )
}

@Composable
fun HomeScreenDrawerHeader(value: String?){
    Box(modifier = Modifier
        .background(Color.LightGray)
        .fillMaxWidth()
        .padding(30.dp),
        ){
        NavigationDrawerText(
            title = value?: stringResource(id = R.string.master_title),
            textUnit = 25.sp
        )
    }
}

@Composable
fun NavigationDrawerBody(navigationDrawerItems: List<NavigationItem>,
                         onNavigationItemClicked: (NavigationItem) -> Unit){
    LazyColumn(modifier = Modifier
        .fillMaxWidth()) {
        items(navigationDrawerItems){
            NavigationItemRow(item = it,
                onNavigationItemClicked = onNavigationItemClicked)
        }
    }
}

@Composable
fun NavigationItemRow(item: NavigationItem, onNavigationItemClicked: (NavigationItem) -> Unit){
    Row(modifier = Modifier
        .clickable {
            onNavigationItemClicked.invoke(item)
        }
        .padding(5.dp)
        .fillMaxWidth()){
        Icon(imageVector =  item.icon, contentDescription = item.description)
        Spacer(modifier = Modifier.width(20.dp))
        NavigationDrawerText(title = item.title, textUnit = 18.sp)
    }
}

@Composable
fun NavigationDrawerText(title: String, textUnit: TextUnit){
    val shadowOffset = Offset(4f, 6f)
    Text(text = title,
        style = TextStyle(
            color = Color.Black,
            fontStyle = FontStyle.Normal,
            fontSize = textUnit,
//                shadow = Shadow(color = Color.Black, offset = shadowOffset)
        )
    )
}