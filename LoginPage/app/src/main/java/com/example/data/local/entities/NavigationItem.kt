package com.example.data.local.entities

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem (
    val title: String,
    val description: String,
    val itemId: String,
    val icon: ImageVector
)