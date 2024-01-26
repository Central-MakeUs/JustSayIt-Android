package com.sowhat.user_presentation.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sowhat.common.model.UiState
import com.sowhat.common.util.LaunchWhenStarted
import com.sowhat.designsystem.R
import com.sowhat.designsystem.common.BUTTON_EDIT_PROFILE
import com.sowhat.designsystem.common.APPBAR_SETTING
import com.sowhat.designsystem.component.AppBar
import com.sowhat.designsystem.component.CenteredCircularProgress
import com.sowhat.designsystem.component.DefaultButtonFull
import com.sowhat.designsystem.theme.JustSayItTheme
import com.sowhat.user_domain.model.UserInfoDomain
import com.sowhat.user_presentation.common.MenuItem
import com.sowhat.user_presentation.component.Menu
import com.sowhat.user_presentation.component.UserProfile
import com.sowhat.user_presentation.navigation.navigateToSignOut
import com.sowhat.user_presentation.navigation.navigateToUpdate

@Composable
fun SettingRoute(
    viewModel: SettingViewModel = hiltViewModel(),
    appNavController: NavHostController
) {
    LaunchWhenStarted {
        viewModel.getUserInfo()
    }

    SettingScreen(
        uiState = viewModel.uiState.collectAsState().value,
        appNavController = appNavController
    )
}

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    uiState: UiState<UserInfoDomain>,
    appNavController: NavHostController
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            AppBar(
                title = APPBAR_SETTING,
                navigationIcon = null,
                actionIcon = null
            )
        }
    ) { paddingValues ->
        if (uiState.isLoading) {
            CenteredCircularProgress()
        } else {
            SettingScreenContent(
                modifier = Modifier.padding(paddingValues),
                appNavController = appNavController,
                uiState = uiState
            )
        }
    }
}

@Composable
private fun SettingScreenContent(
    modifier: Modifier = Modifier,
    appNavController: NavHostController,
    uiState: UiState<UserInfoDomain>
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(JustSayItTheme.Colors.mainBackground)
            .verticalScroll(scrollState)
    ) {
        ProfileSection(
            appNavController = appNavController,
            uiState = uiState
        )

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = JustSayItTheme.Spacing.spaceSm),
            thickness = 0.5.dp,
            color = JustSayItTheme.Colors.subBackground
        )

        // TODO 메뉴 확정 시 하드코딩된 문자열들 리소스화하여 수정하기
        Menu(
            title = stringResource(id = R.string.setting_title_normal),
            menus = listOf(
                MenuItem(
                    title = stringResource(id = R.string.setting_item_contact),
                    trailingIcon = R.drawable.ic_next_24
                ),
                MenuItem(
                    title = stringResource(id = R.string.setting_item_terms),
                    trailingIcon = R.drawable.ic_next_24
                ),
                MenuItem(
                    title = stringResource(id = R.string.setting_item_privacy),
                    trailingIcon = R.drawable.ic_next_24
                ),
                MenuItem(
                    title = stringResource(id = R.string.setting_item_version),
                    trailingText = "Ver.1.0"
                ),
            )
        )
        
        Menu(
            modifier = Modifier.padding(vertical = JustSayItTheme.Spacing.spaceSm),
            title = stringResource(id = R.string.setting_title_setting),
            menus = listOf(
                MenuItem(
                    title = stringResource(id = R.string.setting_item_indie_info),
                    trailingIcon = R.drawable.ic_next_24,
                    onClick = { appNavController.navigateToSignOut() }
                )
            )
        )
    }
}

@Composable
private fun ProfileSection(
    modifier: Modifier = Modifier,
    appNavController: NavHostController,
    uiState: UiState<UserInfoDomain>
) {

    val profileInfo = uiState.data?.profileInfo

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(JustSayItTheme.Colors.mainBackground)
    ) {
        UserProfile(
            userName = profileInfo?.nickname ?: "",
            // TODO 서버에서 넘겨주는 정보 플랫폼 및 이메일 추가되면 변경하기
            platformDrawable = R.drawable.ic_naver_16,
            email = "네이버",
            profileUrl = profileInfo?.profileImg ?: ""
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = JustSayItTheme.Spacing.spaceBase),
            contentAlignment = Alignment.Center
        ) {
            DefaultButtonFull(
                text = BUTTON_EDIT_PROFILE,
                onClick = {
                    appNavController.navigateToUpdate()
                }
            )
        }

        Spacer(modifier = Modifier.height(JustSayItTheme.Spacing.spaceBase))
    }
}

@Preview
@Composable
fun SettingScreenPreview() {
    val navController = rememberNavController()
    SettingScreen(uiState = UiState(), appNavController = navController)
}