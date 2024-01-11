package com.sowhat.user_presentation.setting

import android.widget.Space
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.sowhat.designsystem.R
import com.sowhat.designsystem.common.BUTTON_EDIT_PROFILE
import com.sowhat.designsystem.common.SETTING
import com.sowhat.designsystem.component.AppBar
import com.sowhat.designsystem.component.DefaultButtonFull
import com.sowhat.designsystem.theme.JustSayItTheme
import com.sowhat.user_presentation.common.MenuItem
import com.sowhat.user_presentation.component.Menu
import com.sowhat.user_presentation.component.UserProfile

@Composable
fun SettingRoute(
    viewModel: SettingViewModel = hiltViewModel()
) {
    SettingScreen()
}

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            AppBar(
                title = SETTING,
                navigationIcon = R.drawable.ic_back_24,
                actionIcon = null
            )
        }
    ) { paddingValues ->
        SettingScreenContent(
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
private fun SettingScreenContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        ProfileSection()

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = JustSayItTheme.Spacing.spaceNormal),
            thickness = 0.5.dp,
            color = JustSayItTheme.Colors.subBackground
        )

        // TODO 메뉴 확정 시 하드코딩된 문자열들 리소스화하여 수정하기
        Menu(
            title = "일반",
            menus = listOf(
                MenuItem(
                    title = "개발자에게 연락하기",
                    trailingIcon = R.drawable.ic_next_24
                ),
                MenuItem(
                    title = "이용 약관",
                    trailingIcon = R.drawable.ic_next_24
                ),
                MenuItem(
                    title = "개인정보 보호",
                    trailingIcon = R.drawable.ic_next_24
                ),
                MenuItem(title = "앱 버전", trailingText = "Ver.1.0"),
            )
        )
    }
}

@Composable
private fun ProfileSection() {
    UserProfile(
        userName = "kmkim",
        platformDrawable = R.drawable.ic_naver_16,
        email = "kmkim7575@gmail.com",
        profileUrl = "https://github.com/kmkim2689/Android-Wiki/assets/101035437/88d7b249-ad72-4be9-8d79-38dc942e0a7f"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = JustSayItTheme.Spacing.spaceMedium),
        contentAlignment = Alignment.Center
    ) {
        DefaultButtonFull(
            text = BUTTON_EDIT_PROFILE,
            onClick = {}
        )
    }

    Spacer(modifier = Modifier.height(JustSayItTheme.Spacing.spaceMedium))
}

@Preview
@Composable
fun SettingScreenPreview() {
    SettingScreen()
}