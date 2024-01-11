package com.sowhat.user_presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sowhat.user_presentation.setting.SettingRoute

const val SETTING = "setting"
const val CONFIG_EDIT = "config_edit"

fun NavGraphBuilder.settingScreen() {
    composable(route = SETTING) {
        SettingRoute()
    }
}

fun NavGraphBuilder.configEditScreen() {
    composable(route = CONFIG_EDIT) {

    }
}