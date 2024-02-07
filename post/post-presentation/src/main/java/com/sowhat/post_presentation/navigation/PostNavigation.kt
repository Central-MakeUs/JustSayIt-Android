package com.sowhat.post_presentation.navigation

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sowhat.common.navigation.FEED_EDIT
import com.sowhat.common.navigation.POST
import com.sowhat.post_presentation.edit.EditRoute
import com.sowhat.post_presentation.posting.PostRoute

fun NavGraphBuilder.postScreen(
    appNavController: NavHostController
) {
    composable(route = POST) {
        PostRoute(navController = appNavController)
    }
}

fun NavController.navigateBack() {
    if (this.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
        this.previousBackStackEntry
            ?.savedStateHandle
            ?.set("isPosted", true)
        this.popBackStack()
    }
}

fun NavGraphBuilder.editFeedScreen(
    appNavController: NavHostController
) {
    composable(
        route = "$FEED_EDIT/{from}/{feedId}",
        arguments = listOf(
            navArgument("from") { type = NavType.StringType },
            navArgument("feedId") { type = NavType.LongType }
        )
    ) { backStackEntry ->
        val feedId = backStackEntry.arguments?.getLong("feedId")
        val from = backStackEntry.arguments?.getString("from")
        Log.i("FeedEdit", "editFeedScreen: feed id : $feedId")
        if (feedId != null && from != null) {
            EditRoute(
                navController = appNavController,
                feedId = feedId,
                from = from
            )
        }
    }
}