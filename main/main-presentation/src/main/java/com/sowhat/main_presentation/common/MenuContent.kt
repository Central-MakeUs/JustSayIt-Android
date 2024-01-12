package com.sowhat.main_presentation.common

import com.sowhat.`main-presentation`.R

sealed class MenuContent(
    val title: String,
    val route: String,
    val unselectedIcon: Int,
    val selectedIcon: Int
) {
    object Home: MenuContent(
        title = "홈",
        route = "home",
        unselectedIcon = com.sowhat.designsystem.R.drawable.ic_home_outlined_24,
        selectedIcon = com.sowhat.designsystem.R.drawable.ic_home_default_24
    )

    object Feed: MenuContent(
        title = "피드",
        route = "feeds",
        unselectedIcon = com.sowhat.designsystem.R.drawable.ic_feed_outlined_24,
        selectedIcon = com.sowhat.designsystem.R.drawable.ic_feed_default_24
    )
}
