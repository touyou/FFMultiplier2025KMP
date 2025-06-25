package com.dev.touyou.ffmultiplier.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class AppDestination(val route: String) {
    object Home : AppDestination("home")
    object Ranking : AppDestination("ranking")
    object Settings : AppDestination("settings")
    object Game : AppDestination("game")
}

sealed class TopLevelDestination(route: String, val icon: ImageVector, val title: String) : AppDestination(route) {
    object Home : TopLevelDestination(AppDestination.Home.route, Icons.Default.Home, "Home")
    object Ranking : TopLevelDestination(AppDestination.Ranking.route, Icons.Default.List, "Ranking")
    object Settings : TopLevelDestination(AppDestination.Settings.route, Icons.Default.Settings, "Settings")
}

val TOP_LEVEL_DESTINATIONS = listOf(
    TopLevelDestination.Home,
    TopLevelDestination.Ranking,
    TopLevelDestination.Settings
)