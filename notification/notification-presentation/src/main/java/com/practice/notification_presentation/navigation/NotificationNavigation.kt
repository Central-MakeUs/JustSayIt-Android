package com.practice.notification_presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.practice.notification_presentation.notifications.NotificationRoute
import com.sowhat.common.navigation.NOTIFICATION

fun NavGraphBuilder.notificationScreen(
    appNavController: NavHostController
) {
    composable(
        route = NOTIFICATION,
        enterTransition = {
            EnterTransition.None
        },
        exitTransition = {
            ExitTransition.None
        },
        popEnterTransition = null,
        popExitTransition = null
    ) {
        NotificationRoute(navController = appNavController)
    }
}