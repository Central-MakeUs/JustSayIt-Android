package com.sowhat.main_presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
import com.sowhat.designsystem.common.bottomBorder
import com.sowhat.designsystem.common.topBorder
import com.sowhat.designsystem.theme.Gray300
import com.sowhat.designsystem.theme.JustSayItTheme
import com.sowhat.main_presentation.common.CenterNavItemContent
import com.sowhat.main_presentation.common.MenuContent

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    mainNavController: NavHostController,
    bottomNavItemList: List<MenuContent>,
    centerNavItem: CenterNavItemContent,
    bottomNavBackground: Color,
    onNavItemClick: (MenuContent) -> Unit,
    onCenterNavItemClick: () -> Unit
) {
    val navBackStackEntry = mainNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry.value?.destination?.route

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(NAVIGATION_HEIGHT.dp)
            .background(Color.Transparent)
    ) {

        val (navigationBar, floatingButton) = createRefs()
        val floatingButtonSpace = 6.dp

        NavigationBar(
            modifier = modifier
                .constrainAs(navigationBar) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .background(JustSayItTheme.Colors.mainBackground)
                .topBorder(strokeWidth = JustSayItTheme.Spacing.border, color = Gray300),
//            .border(width = 0.5.dp, color = Gray300)
            containerColor = bottomNavBackground
//            containerColor = JustSayItTheme.Colors.mainSurface,
//            contentColor = JustSayItTheme.Colors.onMainSurface
        ) {
            bottomNavItemList.forEachIndexed { index, navItem ->
                val isSelected = currentDestination == navItem.route

                if (index == 2) Spacer(modifier = Modifier.width(58.dp))

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
            centerNavItemIcon = centerNavItem.centerNavItemIcon,
            centerNavItemIconTint = centerNavItem.centerNavItemIconTint,
            centerItemBackground = centerNavItem.centerItemBackground,
            onCenterNavItemClick = onCenterNavItemClick
        )
    }
}

@Composable
fun RowScope.BottomNavigationItem(
    selectedItem: MenuContent,
    isItemSelected: Boolean,
    selectedIcon: Int,
    unSelectedIcon: Int,
    navItemOnClick: (MenuContent) -> Unit,
) {

    NavigationBarItem(
        modifier = Modifier.padding(vertical = JustSayItTheme.Spacing.spaceXXS),
        selected = isItemSelected,
        onClick = { navItemOnClick(selectedItem) },
        icon = {
            val currentIcon = if (isItemSelected) selectedIcon else unSelectedIcon
            val tint = if (isItemSelected) JustSayItTheme.Colors.mainTypo else JustSayItTheme.Colors.inactiveTypo
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = currentIcon),
                    contentDescription = selectedItem.title,
                    tint = tint
                )

                Text(
                    text = selectedItem.title,
                    color = tint,
                    style = JustSayItTheme.Typography.detail1
                )
            }
        },
        colors = NavigationBarItemDefaults.colors(
            indicatorColor = JustSayItTheme.Colors.mainBackground
        ),
        interactionSource = remember { MutableInteractionSource() }
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
    FilledTonalIconButton(
        modifier = modifier,
        onClick = onCenterNavItemClick,
        shape = JustSayItTheme.Shapes.circle,
        colors = IconButtonDefaults.filledTonalIconButtonColors(
            containerColor = JustSayItTheme.Colors.mainTypo,
            contentColor = JustSayItTheme.Colors.mainBackground
        )
    ) {
        Icon(
            painter = painterResource(id = centerNavItemIcon),
            contentDescription = "fab button",
            tint = centerNavItemIconTint
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun BottomNavigationPreview() {

    val navController = rememberNavController()

    BottomNavigationBar(
        mainNavController = navController,
        bottomNavItemList = listOf(
            MenuContent.Home,
            MenuContent.My
        ),
        centerNavItem = CenterNavItemContent(
            R.drawable.ic_add_24,
            JustSayItTheme.Colors.mainBackground,
            JustSayItTheme.Colors.mainTypo
        ),
        bottomNavBackground = Color.White,
        onNavItemClick = {},
        onCenterNavItemClick = {}
    )
}