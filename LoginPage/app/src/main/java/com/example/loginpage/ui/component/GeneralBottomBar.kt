package com.example.loginpage.ui.component

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.loginpage.R

@SuppressLint("RememberReturnType")
@Composable
fun GeneralBottomAppBar(navController: NavHostController){

    val context = LocalContext.current.applicationContext
    val selected = remember{mutableStateOf(Icons.Default.Home)}
    BottomAppBar(
        containerColor = Color.LightGray,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp))) {
        Row(modifier = Modifier
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround) {

            IconButton(onClick = {
                getToast(context, action = "Home Nav button clicked!")
                selected.value = Icons.Default.Home
            }) {
                Icon(imageVector = Icons.Default.Home,
                    modifier = Modifier.size(26.dp),
                    contentDescription = stringResource(id = R.string.home),
                    tint = if (selected.value == Icons.Default.Home) {
                        Color.Blue
                    }else{
                        Color.Black
                    })
                }
            
            IconButton(onClick = {
                selected.value = Icons.Default.Edit
                getToast(context, action = "Edit Nav button clicked!")
            }) {
                Icon(imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                    tint = if (selected.value == Icons.Default.Edit) Color.Blue else Color.Black)
            }

            IconButton(onClick = {
                selected.value = Icons.Default.Delete
                getToast(context, action = "Delete Nav button clicked!") }) {
                Icon(imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = if (selected.value == Icons.Default.Delete) Color.Blue else Color.Black)
            }

            IconButton(onClick = {
                selected.value = Icons.Default.Person
                getToast(context, action = "Person Nav button clicked!")
            }) {
                Icon(imageVector = Icons.Default.Person,
                    contentDescription = "Person",
                    tint = if (selected.value == Icons.Default.Person) Color.Blue else Color.Black)
            }

            IconButton(onClick = {
                selected.value = Icons.Default.Settings
                getToast(context, action = "Setting Nav button clicked!")
            }) {
                Icon(imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = if (selected.value == Icons.Default.Settings) Color.Blue else Color.Black)
            }
        }
    }
}

fun getToast(context: Context, action: String, toastDuration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, action, toastDuration).show()
}