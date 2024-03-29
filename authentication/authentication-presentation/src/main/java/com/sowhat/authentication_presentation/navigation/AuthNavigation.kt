package com.sowhat.authentication_presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sowhat.common.navigation.CONFIGURATION
import com.sowhat.common.navigation.MAIN
import com.sowhat.common.navigation.ONBOARDING
import com.sowhat.authentication_presentation.configuration.UserConfigRoute
import com.sowhat.authentication_presentation.onboarding.OnboardingRoute
import com.sowhat.authentication_presentation.splash.SplashRoute
import com.sowhat.common.navigation.SPLASH

fun NavGraphBuilder.splashScreen(
    navController: NavHostController
) {
    composable(route = SPLASH) {
        SplashRoute(navController = navController)
    }
}

fun NavGraphBuilder.onBoardingScreen(
    navController: NavHostController
) {
    composable(route = ONBOARDING) {
        OnboardingRoute(navController = navController)
    }
}

fun NavController.navigateToOnboarding(
    popUpTo: String
) {
    this.navigate(ONBOARDING) {
        popUpTo(popUpTo) {
            inclusive = true
        }
        launchSingleTop = true
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

fun NavController.navigateToMain(popUpTo: String) {
    this.navigate(MAIN) {
        popUpTo(popUpTo) {
            inclusive = true
        }
        launchSingleTop = true
    }
}
