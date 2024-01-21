package com.sowhat.main_presentation.common

import androidx.compose.ui.graphics.Color
import com.sowhat.main_presentation.R

sealed class MenuContent(
    val title: String,
    val route: String,
    val unselectedIcon: Int,
    val selectedIcon: Int,
) {
    object Home : MenuContent(
        title = "홈",
        route = "home",
        unselectedIcon = com.sowhat.designsystem.R.drawable.ic_home_outlined_24,
        selectedIcon = com.sowhat.designsystem.R.drawable.ic_home_default_24,
    )

    object My : MenuContent(
        title = "내 글",
        route = "my",
        unselectedIcon = com.sowhat.designsystem.R.drawable.ic_my_outlined_24,
        selectedIcon = com.sowhat.designsystem.R.drawable.ic_my_default_24
    )

    object Notification : MenuContent(
        title = "알림",
        route = "notification",
        unselectedIcon = com.sowhat.designsystem.R.drawable.ic_notification_outlined_24,
        selectedIcon = com.sowhat.designsystem.R.drawable.ic_notification_default_24
    )

    object Setting : MenuContent(
        title = "더보기",
        route = "setting",
        unselectedIcon = com.sowhat.designsystem.R.drawable.ic_menu_inactive_24,
        selectedIcon = com.sowhat.designsystem.R.drawable.ic_menu_active_24
    )
}
