package com.sowhat.feed_presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.sowhat.common.navigation.HOME
import com.sowhat.feed_presentation.feeds.FeedRoute

fun NavGraphBuilder.homeScreen(
    appNavController: NavHostController
) {
    composable(
        route = HOME,
        enterTransition = {
            EnterTransition.None
        },
        exitTransition = {
            ExitTransition.None
        },
        popEnterTransition = null,
        popExitTransition = null
    ) {
        FeedRoute(navController = appNavController)
    }
}