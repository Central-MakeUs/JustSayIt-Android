package com.sowhat.justsayit.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.sowhat.common.navigation.ONBOARDING
import com.sowhat.main_presentation.navigation.mainScreen
import com.sowhat.authentication_presentation.navigation.onBoardingScreen
import com.sowhat.authentication_presentation.navigation.splashScreen
import com.sowhat.authentication_presentation.navigation.userConfigScreen
import com.sowhat.common.model.FCMData
import com.sowhat.common.navigation.SPLASH
import com.sowhat.post_presentation.navigation.postScreen
import com.sowhat.user_presentation.navigation.signOutScreen
import com.sowhat.user_presentation.navigation.userInfoUpdateScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    fcmData: FCMData?
) {
    NavHost(navController = navController, startDestination = SPLASH) {
        splashScreen(navController = navController)
        onBoardingScreen(navController = navController)
        userConfigScreen(navController = navController)
        userInfoUpdateScreen(appNavController = navController)
        mainScreen(appNavController = navController, snackbarHostState = snackbarHostState)
        postScreen(appNavController = navController)
        userInfoUpdateScreen(appNavController = navController)
        signOutScreen(appNavController = navController)
    }
}