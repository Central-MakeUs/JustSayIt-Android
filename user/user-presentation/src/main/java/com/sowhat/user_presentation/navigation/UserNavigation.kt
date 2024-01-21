package com.sowhat.user_presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.sowhat.common.navigation.CONFIG_EDIT
import com.sowhat.common.navigation.SETTING
import com.sowhat.user_presentation.edit.UpdateRoute
import com.sowhat.user_presentation.setting.SettingRoute

fun NavGraphBuilder.settingScreen(
    appNavController: NavHostController
) {
    composable(route = SETTING) {
        SettingRoute(navController = appNavController)
    }
}

fun NavController.navigateToUpdate() {
    this.navigate(SETTING)
}

fun NavGraphBuilder.userInfoUpdateScreen(
    navController: NavHostController
) {
    composable(route = CONFIG_EDIT) {
        UpdateRoute(navController = navController)
    }
}

fun NavController.navigateUpToSetting() {
    this.navigate(SETTING) {
        popUpTo(CONFIG_EDIT) {
            inclusive = true
        }
        launchSingleTop = true
    }
}