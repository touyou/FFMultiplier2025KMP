package com.dev.touyou.ffmultiplier.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dev.touyou.ffmultiplier.components.GameScreen
import com.dev.touyou.ffmultiplier.ui.screens.HomeScreen
import com.dev.touyou.ffmultiplier.ui.screens.RankingScreen
import com.dev.touyou.ffmultiplier.ui.screens.SettingsScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AppDestination.Home.route,
        modifier = modifier
    ) {
        composable(AppDestination.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(AppDestination.Ranking.route) {
            RankingScreen()
        }
        composable(AppDestination.Settings.route) {
            SettingsScreen()
        }
        composable(AppDestination.Game.route) {
            GameScreen()
        }
    }
}