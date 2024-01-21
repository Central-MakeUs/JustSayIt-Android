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
        SettingRoute(appNavController = appNavController)
    }
}

fun NavController.navigateToUpdate() {
    this.navigate(CONFIG_EDIT)
}

fun NavGraphBuilder.userInfoUpdateScreen(
    appNavController: NavHostController
) {
    composable(route = CONFIG_EDIT) {
        UpdateRoute(appNavController = appNavController)
    }
}

fun NavController.navigateUpToSetting() {
    this.popBackStack()
}