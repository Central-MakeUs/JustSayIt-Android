package com.sowhat.main_presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.sowhat.common.navigation.MAIN
import com.sowhat.common.navigation.POST
import com.sowhat.main_presentation.common.MenuContent
import com.sowhat.main_presentation.ui.MainRoute

fun NavGraphBuilder.mainScreen(
    appNavController: NavHostController
) {
    composable(route = MAIN) {
        MainRoute(appNavController = appNavController)
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
            inclusive = true
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}