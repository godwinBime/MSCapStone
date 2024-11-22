package com.example.data.local.entities

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings

val navigationItemList = listOf<NavigationItem>(
    NavigationItem(
        title = "Home",
        description = "Home Screen",
        itemId = "homeScreen",
        icon = Icons.Default.Home),

    NavigationItem(
        title = "Profile",
        description = "Profile Screen",
        itemId = "profileScreen",
        icon = Icons.Default.Person),

    NavigationItem(
        title = "Change Password",
        description = "Change Password Screen",
        itemId = "changePassword",
        icon = Icons.Default.Password),

    NavigationItem(
        title = "Settings",
        description = "Settings Screen",
        itemId = "settingsScreen",
        icon = Icons.Default.Settings),

    NavigationItem(
        title = "Logout",
        description = "Logout Screen",
        itemId = "logoutScreen",
        icon = Icons.AutoMirrored.Filled.Logout
    )
)