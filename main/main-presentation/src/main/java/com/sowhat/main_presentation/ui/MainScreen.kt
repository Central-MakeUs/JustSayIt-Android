package com.sowhat.main_presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sowhat.common.navigation.POST
import com.sowhat.designsystem.theme.JustSayItTheme
import com.sowhat.main_presentation.common.CenterNavItemContent
import com.sowhat.main_presentation.common.MenuContent
import com.sowhat.main_presentation.component.BottomNavigationBar
import com.sowhat.main_presentation.navigation.MainNavHost

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

    val menus = listOf(MenuContent.Home, MenuContent.My, MenuContent.Notification, MenuContent.Setting)

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
                onNavItemClick = {
                    mainNavController.navigate(it.route) {
                        popUpTo(mainNavController.graph.findStartDestination().id) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                onCenterNavItemClick = {
                    mainNavController.navigate(POST) {
                        launchSingleTop = true
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            MainNavHost(
                appNavController = appNavController,
                mainNavController = mainNavController,
            )
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    val navController = rememberNavController()
    MainScreen(appNavController = navController)
}