package com.sowhat.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sowhat.presentation.configuration.UserConfigRoute
import com.sowhat.presentation.onboarding.OnboardingRoute
import com.sowhat.presentation.onboarding.OnboardingScreen

const val SPLASH = "splash"
const val ONBOARDING = "onboarding"
const val CONFIGURATION = "configuration"
const val MAIN = "main"

fun NavGraphBuilder.onBoardingScreen(
    navController: NavHostController
) {
    composable(route = ONBOARDING) {
        OnboardingRoute(navController = navController)
    }
}

fun NavController.navigateToUserConfig(navOptions: NavOptions? = null) {
    this.navigate(CONFIGURATION, navOptions)
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
