package com.emmanuelguther.features

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.ExperimentalCoroutinesApi

sealed class Routes(val path: String) {
    object Splash : Routes("splash")
    object Main : Routes("main")
}

@ExperimentalCoroutinesApi
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.Splash.path) {

    }
}


