package com.example.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun HandlePasswordVisibility(){
    val showPassword = remember { mutableStateOf(false) }
    if (showPassword.value){
        VisualTransformation.None
    } else{
        PasswordVisualTransformation()
    }
}

@Composable
fun HandlePasswordInvisibility(){
    val showPassword = remember { mutableStateOf(true) }
    if (showPassword.value){
        IconButton(onClick = { showPassword.value = false }) {
            Icon(
                imageVector = Icons.Filled.Visibility,
                contentDescription = "Hide Password",
                tint = Color.Black
            )
        }
    }else{
        IconButton(onClick = { showPassword.value = true }) {
            Icon(
                imageVector = Icons.Filled.VisibilityOff,
                contentDescription = "Show Password",
                tint = Color.Black
            )
        }
    }
}