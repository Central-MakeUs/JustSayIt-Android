package com.sowhat.user_presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sowhat.common.navigation.CONFIG_EDIT
import com.sowhat.common.navigation.SETTING
import com.sowhat.user_presentation.edit.UpdateRoute
import com.sowhat.user_presentation.setting.SettingRoute

fun NavGraphBuilder.settingScreen() {
    composable(route = SETTING) {
        SettingRoute()
    }
}

fun NavGraphBuilder.configEditScreen() {
    composable(route = CONFIG_EDIT) {
        UpdateRoute()
    }
}