package com.sowhat.user_presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sowhat.common.CONFIG_EDIT
import com.sowhat.common.SETTING
import com.sowhat.user_presentation.edit.EditRoute
import com.sowhat.user_presentation.setting.SettingRoute

fun NavGraphBuilder.settingScreen() {
    composable(route = SETTING) {
        SettingRoute()
    }
}

fun NavGraphBuilder.configEditScreen() {
    composable(route = CONFIG_EDIT) {
        EditRoute()
    }
}