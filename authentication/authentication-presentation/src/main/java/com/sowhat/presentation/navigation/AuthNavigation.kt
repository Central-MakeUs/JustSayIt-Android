package com.sowhat.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sowhat.presentation.onboarding.OnboardingRoute
import com.sowhat.presentation.onboarding.OnboardingScreen

const val SPLASH = "splash"
const val ONBOARDING = "onboarding"

fun NavGraphBuilder.onBoardingScreen() {
    composable(route = ONBOARDING) {
        OnboardingRoute()
    }
}