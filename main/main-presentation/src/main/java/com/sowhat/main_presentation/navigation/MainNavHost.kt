package com.sowhat.main_presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.sowhat.common.navigation.HOME
import com.sowhat.common.navigation.MAIN
import com.sowhat.post_presentation.navigation.postScreen
import com.sowhat.report_presentation.navigation.myScreen
import com.sowhat.user_presentation.navigation.settingScreen

@Composable
fun MainNavHost(
    appNavController: NavHostController,
    mainNavController: NavHostController,
) {
    NavHost(
        navController = mainNavController,
        startDestination = HOME,
        route = MAIN
    ) {
        mainScreen(appNavController = appNavController)
        myScreen(appNavController = appNavController)
        postScreen(appNavController = appNavController)
        settingScreen(appNavController = appNavController)
    }
}