package com.sowhat.user_presentation.signout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sowhat.designsystem.component.AppBar
import com.sowhat.designsystem.R
import com.sowhat.designsystem.theme.JustSayItTheme
import com.sowhat.user_presentation.common.MenuItem
import com.sowhat.user_presentation.component.Menu
import com.sowhat.user_presentation.navigation.navigateUpToSetting

@Composable
fun SignOutRoute(
    appNavController: NavController
) {
    SignOutScreen(appNavController = appNavController)
}

@Composable
fun SignOutScreen(
    appNavController: NavController
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        contentColor = contentColorFor(
            backgroundColor = JustSayItTheme.Colors.mainBackground
        ),
        topBar = {
            AppBar(
                title = stringResource(id = R.string.setting_item_indie_info),
                navigationIcon = R.drawable.ic_back_24,
                actionIcon = null,
                onNavigationIconClick = { appNavController.navigateUpToSetting() }
            )
        }
    ) { paddingValues ->
        SignOutScreenContent(paddingValues = paddingValues)
    }
}

@Composable
private fun SignOutScreenContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Menu(
            modifier = Modifier
                .padding(vertical = JustSayItTheme.Spacing.spaceSm),
            title = null,
            menus = listOf(
                MenuItem(
                    title = stringResource(id = R.string.setting_item_sign_out),
                    onClick = {
                        // TODO 로그아웃 이후 화면 이동 구현
                    }
                ),
                MenuItem(
                    title = stringResource(id = R.string.setting_item_withdraw),
                    onClick = {
                        // TODO 회원탈퇴 성공 후 화면 이동 구현
                    }
                )
            )
        )
    }
}

@Preview
@Composable
fun SignOutScreenPreview() {
    SignOutScreen(appNavController = rememberNavController())
}