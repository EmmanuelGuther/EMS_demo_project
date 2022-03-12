package com.emmanuelguther.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.emmanuelguther.presentation.features.main.MainScreen
import com.emmanuelguther.presentation.features.main.MainViewModel
import com.emmanuelguther.presentation.features.splash.SplashScreen
import com.emmanuelguther.presentation.features.splash.SplashViewModel
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
        splashNavRoute(navController)
        mainNavRoute(navController)
    }
}

@ExperimentalCoroutinesApi
@OptIn(ExperimentalComposeUiApi::class)
private fun NavGraphBuilder.splashNavRoute(navController: NavHostController) {
    composable(Routes.Splash.path) {
        val viewModel: SplashViewModel = hiltViewModel()
        SplashScreen(
            viewModel,
            onNavigateToMain = {
                navController.navigate(Routes.Main.path)
            }
        )
    }
}

@ExperimentalCoroutinesApi
@OptIn(ExperimentalComposeUiApi::class)
private fun NavGraphBuilder.mainNavRoute(navController: NavHostController) {
    composable(Routes.Main.path) {
        val viewModel: MainViewModel = hiltViewModel()
        MainScreen(
            viewModel,
            onNavigateToDetail = {}
        )
    }
}

