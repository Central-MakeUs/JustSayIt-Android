package com.sowhat.main_presentation.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.sowhat.notification_presentation.navigation.notificationScreen
import com.sowhat.common.navigation.HOME
import com.sowhat.common.navigation.MAIN
import com.sowhat.feed_presentation.navigation.homeScreen
import com.sowhat.report_presentation.navigation.myScreen
import com.sowhat.user_presentation.navigation.settingScreen

@Composable
fun MainNavGraph(
    appNavController: NavHostController,
    mainNavController: NavHostController,
    snackbarHostState: SnackbarHostState
) {
    NavHost(
        navController = mainNavController,
        startDestination = HOME,
        route = MAIN
    ) {
        homeScreen(
            appNavController = appNavController,
            snackbarHostState = snackbarHostState
        )
        myScreen(
            appNavController = appNavController,
            snackbarHostState = snackbarHostState
        )
        notificationScreen(appNavController = appNavController)
        settingScreen(appNavController = appNavController)
    }
}