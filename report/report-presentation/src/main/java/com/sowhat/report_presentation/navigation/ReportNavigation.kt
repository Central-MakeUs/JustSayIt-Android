package com.sowhat.report_presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.sowhat.common.navigation.FEED_EDIT
import com.sowhat.common.navigation.MY
import com.sowhat.common.navigation.ONBOARDING
import com.sowhat.report_presentation.mypage.MyPageRoute

fun NavGraphBuilder.myScreen(
    appNavController: NavHostController,
    snackbarHostState: SnackbarHostState
) {
    composable(
        route = MY,
        enterTransition = {
            EnterTransition.None
        },
        exitTransition = {
            ExitTransition.None
        },
        popEnterTransition = null,
        popExitTransition = null
    ) {
        MyPageRoute(navController = appNavController, snackbarHostState = snackbarHostState)
    }
}

fun NavController.navigateToEditScreen(feedId: Long) {
    this.navigate("$FEED_EDIT/$MY/$feedId") {
        launchSingleTop = true
    }
}