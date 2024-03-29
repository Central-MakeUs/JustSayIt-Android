package com.sowhat.feed_presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.sowhat.common.navigation.FEED_EDIT
import com.sowhat.common.navigation.HOME
import com.sowhat.feed_presentation.feeds.FeedRoute

fun NavGraphBuilder.homeScreen(
    appNavController: NavHostController,
    snackbarHostState: SnackbarHostState
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
        FeedRoute(
            navController = appNavController,
            snackbarHostState = snackbarHostState,
            isPosted = appNavController
                .currentBackStackEntry
                ?.savedStateHandle
                ?.getStateFlow("isPosted", false)
        )
    }
}

fun NavController.navigateToEditScreen(feedId: Long) {
    this.navigate("$FEED_EDIT/$HOME/$feedId") {
        launchSingleTop = true
    }
}
