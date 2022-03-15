@file:OptIn(ExperimentalComposeUiApi::class)

package com.emmanuelguther.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.emmanuelguther.presentation.features.detail.DetailScreen
import com.emmanuelguther.presentation.features.detail.DetailViewModel
import com.emmanuelguther.presentation.features.main.MainScreen
import com.emmanuelguther.presentation.features.main.MainViewModel
import com.emmanuelguther.presentation.features.splash.SplashScreen
import com.emmanuelguther.presentation.features.splash.SplashViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

sealed class Routes(val path: String) {
    object Splash : Routes("splash")
    object Main : Routes("main")
    object Detail : Routes("detail/{solar}/{grid}/{quasar}") {
        const val argNameSolar: String = "solar"
        const val argNameGrid: String = "grid"
        const val argNameQuasar: String = "quasar"
        fun pathFor(solar: String, grid: String, quasar: String): String = "detail/${solar}/${grid}/${quasar}"
    }
}

@ExperimentalCoroutinesApi
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.Splash.path) {
        splashNavRoute(navController)
        mainNavRoute(navController)
        detailNavRoute(navController)
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
        MainScreen(viewModel) { hourEnergyHistoric ->
            navController.navigate(
                Routes.Detail.pathFor(
                    hourEnergyHistoric.pvActivePower.toString(),
                    hourEnergyHistoric.gridActivePower.toString(), hourEnergyHistoric.quasarsActivePower.toString()
                )
            )
        }


    }
}

private fun NavGraphBuilder.detailNavRoute(navController: NavHostController) {
    composable(
        Routes.Detail.path,
        arguments = listOf(
            navArgument(Routes.Detail.argNameSolar) {
                type = NavType.StringType
            },
            navArgument(Routes.Detail.argNameGrid) {
                type = NavType.StringType
            },
            navArgument(Routes.Detail.argNameQuasar) {
                type = NavType.StringType
            }
        )
    ) { backStackEntry ->
        val solar = backStackEntry.arguments?.getString(Routes.Detail.argNameSolar) ?: ""
        val grid = backStackEntry.arguments?.getString(Routes.Detail.argNameGrid) ?: ""
        val quasar = backStackEntry.arguments?.getString(Routes.Detail.argNameQuasar) ?: ""
        val viewModel: DetailViewModel = hiltViewModel()
        viewModel.solar = solar
        viewModel.grid = grid
        viewModel.quasar = quasar
        DetailScreen(viewModel) {
            navController.popBackStack()
        }
    }
}

