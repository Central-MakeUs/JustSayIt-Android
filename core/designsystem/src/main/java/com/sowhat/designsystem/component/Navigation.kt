package com.sowhat.designsystem.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sowhat.designsystem.R
import com.sowhat.designsystem.common.NAVIGATION_HEIGHT
import com.sowhat.designsystem.theme.Gray300
import com.sowhat.designsystem.theme.JustSayItTheme

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    bottomNavItemList: List<BottomNavItem>,
    centerNavItemIcon: Int,
    centerNavItemIconTint: Color,
    centerItemBackground: Color,
    bottomNavBackground: Color,
    onNavItemClick: (BottomNavItem) -> Unit,
    onCenterNavItemClick: () -> Unit
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry.value?.destination?.route

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(NAVIGATION_HEIGHT.dp)
    ) {

        val (navigationBar, floatingButton) = createRefs()
        val floatingButtonSpace = JustSayItTheme.Spacing.spaceMedium

        NavigationBar(
            modifier = modifier
                .constrainAs(navigationBar) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .border(width = 0.5.dp, color = Gray300),
            containerColor = bottomNavBackground
//            containerColor = JustSayItTheme.Colors.mainSurface,
//            contentColor = JustSayItTheme.Colors.onMainSurface
        ) {
            bottomNavItemList.forEachIndexed { index, navItem ->
                val isSelected = currentDestination == navItem.route

                if (index != 0) Spacer(modifier = Modifier.width(58.dp))

                BottomNavigationItem(
                    selectedItem = navItem,
                    isItemSelected = isSelected,
                    selectedIcon = navItem.selectedIcon,
                    unSelectedIcon = navItem.unselectedIcon,
                    navItemOnClick = onNavItemClick
                )
            }
        }

        NavigationCenterItem(
            modifier = Modifier
                .constrainAs(floatingButton) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom, margin = floatingButtonSpace)
                }
                .size(JustSayItTheme.Spacing.spaceExtraExtraLarge),
            centerNavItemIcon = centerNavItemIcon,
            centerNavItemIconTint = centerNavItemIconTint,
            centerItemBackground = centerItemBackground,
            onCenterNavItemClick = onCenterNavItemClick
        )
    }
}

@Composable
fun RowScope.BottomNavigationItem(
    selectedItem: BottomNavItem,
    isItemSelected: Boolean,
    selectedIcon: Int,
    unSelectedIcon: Int,
    navItemOnClick: (BottomNavItem) -> Unit,
) {

    NavigationBarItem(
        selected = isItemSelected,
        onClick = { navItemOnClick(selectedItem) },
        icon = {
            val currentIcon = if (isItemSelected) selectedIcon else unSelectedIcon

            Icon(
                painter = painterResource(id = currentIcon),
                contentDescription = selectedItem.title,
                tint = JustSayItTheme.Colors.onMainSurface
            )
        }
    )
}

@Composable
fun NavigationCenterItem(
    modifier: Modifier = Modifier,
    centerNavItemIcon: Int,
    centerNavItemIconTint: Color,
    centerItemBackground: Color,
    onCenterNavItemClick: () -> Unit
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = onCenterNavItemClick,
        containerColor = centerItemBackground,
        shape = JustSayItTheme.Shapes.circle
    ) {
        Icon(
            painter = painterResource(id = centerNavItemIcon),
            contentDescription = "fab button",
            tint = centerNavItemIconTint
        )
    }
}

data class BottomNavItem(
    val route: String,
    val title: String,
    val selectedIcon: Int,
    val unselectedIcon: Int
)

@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun BottomNavigationPreview() {

    val navController = rememberNavController()

    BottomNavigationBar(
        navController = navController,
        bottomNavItemList = listOf(
            BottomNavItem("home", "home", R.drawable.ic_menu_24, R.drawable.ic_camera_24),
            BottomNavItem("feed", "feed", R.drawable.ic_pen_24, R.drawable.ic_back_24),
        ),
        centerNavItemIcon = R.drawable.ic_pen_24,
        centerItemBackground = Color.Black,
        centerNavItemIconTint = Color.White,
        bottomNavBackground = Color.White,
        onNavItemClick = {},
        onCenterNavItemClick = {}
    )
}