package com.sowhat.main_presentation.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sowhat.designsystem.theme.JustSayItTheme
import com.sowhat.main_presentation.common.CenterNavItemContent
import com.sowhat.main_presentation.common.MenuContent
import com.sowhat.main_presentation.component.BottomNavigationBar

@Composable
fun MainRoute(
    appNavController: NavHostController,
) {
    MainScreen(
        appNavController = appNavController
    )
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    appNavController: NavHostController,
) {
    val mainNavController = rememberNavController()
    val scope = rememberCoroutineScope()

    val menus = listOf(
        MenuContent.Home, MenuContent.Feed
    )

    val centerNavItem = CenterNavItemContent(
        centerNavItemIconTint = JustSayItTheme.Colors.mainBackground,
        centerItemBackground = JustSayItTheme.Colors.mainTypo,
        centerNavItemIcon = com.sowhat.designsystem.R.drawable.ic_add_24
    )

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(
                navController = mainNavController,
                bottomNavItemList = menus,
                centerNavItem = centerNavItem,
                bottomNavBackground = JustSayItTheme.Colors.mainSurface,
                onNavItemClick = { },
                onCenterNavItemClick = { }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {

        }
    }
}