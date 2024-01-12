package com.sowhat.main_presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.sowhat.main_presentation.ui.MainRoute

const val MAIN = "main"

fun NavGraphBuilder.mainScreen(
    appNavController: NavHostController
) {
    composable(route = MAIN) {
        MainRoute(appNavController = appNavController)
    }
}