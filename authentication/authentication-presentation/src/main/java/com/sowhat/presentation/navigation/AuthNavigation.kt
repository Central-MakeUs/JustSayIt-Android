package com.sowhat.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sowhat.common.navigation.CONFIGURATION
import com.sowhat.common.navigation.MAIN
import com.sowhat.common.navigation.ONBOARDING
import com.sowhat.presentation.configuration.UserConfigRoute
import com.sowhat.presentation.onboarding.OnboardingRoute

fun NavGraphBuilder.onBoardingScreen(
    navController: NavHostController
) {
    composable(route = ONBOARDING) {
        OnboardingRoute(navController = navController)
    }
}

fun NavController.navigateToUserConfig(navOptions: NavOptions? = null) {
    this.navigate(CONFIGURATION) {
        popUpTo(ONBOARDING) {
            inclusive = true
        }
        launchSingleTop = true
    }
}

fun NavGraphBuilder.userConfigScreen(
    navController: NavHostController
) {
    composable(route = CONFIGURATION) {
        UserConfigRoute(navController = navController)
    }
}

fun NavController.navigateToMain(navOptions: NavOptions? = null) {
    this.navigate(MAIN, navOptions)
}
