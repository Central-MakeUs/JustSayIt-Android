package com.sowhat.main_presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.sowhat.common.navigation.MAIN
import com.sowhat.common.navigation.POST
import com.sowhat.common.navigation.SETTING
import com.sowhat.main_presentation.common.MenuContent
import com.sowhat.main_presentation.ui.MainRoute

fun NavGraphBuilder.mainScreen(
    appNavController: NavHostController,
    snackbarHostState: SnackbarHostState
) {
    composable(
        route = MAIN,
        enterTransition = {
            EnterTransition.None
        },
        exitTransition = {
            ExitTransition.None
        },
        popEnterTransition = null,
        popExitTransition = null
    ) {
        MainRoute(
            appNavController = appNavController,
            snackbarHostState = snackbarHostState
        )
    }
}

fun NavController.navigateToPost() {
    navigate(POST) {
        launchSingleTop = true
    }
}

fun NavController.navigateToMenu(menuItem: MenuContent) {
    navigate(menuItem.route) {
        popUpTo(this@navigateToMenu.graph.findStartDestination().id) {
             saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}