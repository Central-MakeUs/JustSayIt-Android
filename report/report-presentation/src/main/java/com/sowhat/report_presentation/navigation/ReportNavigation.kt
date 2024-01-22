package com.sowhat.report_presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.sowhat.common.navigation.MY
import com.sowhat.common.navigation.ONBOARDING
import com.sowhat.report_presentation.mypage.MyPageRoute

fun NavGraphBuilder.myScreen(
    appNavController: NavHostController
) {
    composable(route = MY) {
        MyPageRoute(navController = appNavController)
    }
}