package com.sowhat.feed_presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.sowhat.common.navigation.HOME
import com.sowhat.feed_presentation.feeds.FeedRoute

fun NavGraphBuilder.homeScreen(
    navController: NavHostController
) {
    composable(route = HOME) {
        FeedRoute(navController = navController)
    }
}