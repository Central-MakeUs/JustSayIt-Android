package com.sowhat.user_presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.sowhat.common.navigation.CONFIG_EDIT
import com.sowhat.common.navigation.MAIN
import com.sowhat.common.navigation.ONBOARDING
import com.sowhat.common.navigation.SETTING
import com.sowhat.common.navigation.SIGN_OUT
import com.sowhat.user_presentation.edit.UpdateRoute
import com.sowhat.user_presentation.setting.SettingRoute
import com.sowhat.user_presentation.signout.SignOutRoute

fun NavGraphBuilder.settingScreen(
    appNavController: NavHostController
) {
    composable(route = SETTING) {
        SettingRoute(appNavController = appNavController)
    }
}

fun NavController.navigateToUpdate() {
    this.navigate(CONFIG_EDIT) {
        launchSingleTop = true
    }
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

fun NavGraphBuilder.signOutScreen(
    appNavController: NavHostController
) {
    composable(route = SIGN_OUT) {
        SignOutRoute(appNavController = appNavController)
    }
}

fun NavController.navigateToSignOut() {
    this.navigate(SIGN_OUT) {
        launchSingleTop = true
    }
}

fun NavController.navigateToOnboarding() {
    this.navigate(ONBOARDING) {
        // popUpTo의 경우 들어가는 route는 해당 route 위에 쌓여있는 모든 화면을 빼겠다는 의미. 따라서 SIGN_OUT이 아닌 MAIN으로 명시
        // inclusive를 true로 하면 해당 route도 포함시켜서 뺀다는 의미
        popUpTo(MAIN) {
            inclusive = true
        }
        launchSingleTop = true
    }
}