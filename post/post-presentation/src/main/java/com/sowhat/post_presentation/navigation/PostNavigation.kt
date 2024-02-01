package com.sowhat.post_presentation.navigation

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.sowhat.common.navigation.POST
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
        this.popBackStack()
    }
}