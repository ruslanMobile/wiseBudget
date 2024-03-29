package com.finance.presentation.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomNavItem(val route: String, val icon: ImageVector) {
    Categories(Screen.Categories.route, Icons.Default.Home),
    Profile(Screen.Profile.route, Icons.Default.AccountCircle)
}