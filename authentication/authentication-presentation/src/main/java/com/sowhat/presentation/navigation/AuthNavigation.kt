package com.sowhat.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sowhat.presentation.configuration.UserConfigRoute
import com.sowhat.presentation.onboarding.OnboardingRoute
import com.sowhat.presentation.onboarding.OnboardingScreen

const val SPLASH = "splash"
const val ONBOARDING = "onboarding"
const val CONFIGURATION = "configuration"

fun NavGraphBuilder.onBoardingScreen() {
    composable(route = ONBOARDING) {
        OnboardingRoute()
    }
}

fun NavGraphBuilder.userConfigScreen() {
    composable(route = CONFIGURATION) {
        UserConfigRoute()
    }
}