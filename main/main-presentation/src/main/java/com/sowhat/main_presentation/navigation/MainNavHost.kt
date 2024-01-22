package com.sowhat.main_presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navigation
import com.practice.notification_presentation.navigation.notificationScreen
import com.sowhat.common.navigation.HOME
import com.sowhat.common.navigation.MAIN
import com.sowhat.feed_presentation.navigation.homeScreen
import com.sowhat.post_presentation.navigation.postScreen
import com.sowhat.report_presentation.navigation.myScreen
import com.sowhat.user_presentation.navigation.settingScreen
import com.sowhat.user_presentation.navigation.userInfoUpdateScreen

@Composable
fun MainNavGraph(
    appNavController: NavHostController,
    mainNavController: NavHostController,
) {
    NavHost(
        navController = mainNavController,
        startDestination = HOME,
        route = MAIN
    ) {
        homeScreen(appNavController = appNavController)
        myScreen(appNavController = appNavController)

        notificationScreen(appNavController = appNavController)

        settingScreen(appNavController = appNavController)
    }
}