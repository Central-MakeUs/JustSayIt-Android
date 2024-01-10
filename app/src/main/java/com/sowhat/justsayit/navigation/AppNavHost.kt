package com.sowhat.justsayit.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.sowhat.presentation.navigation.ONBOARDING
import com.sowhat.presentation.navigation.onBoardingScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState
) {
    NavHost(navController = navController, startDestination = ONBOARDING) {
        onBoardingScreen()
    }
}