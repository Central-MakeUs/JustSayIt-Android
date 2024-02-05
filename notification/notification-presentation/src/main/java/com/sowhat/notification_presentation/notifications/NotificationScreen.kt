package com.sowhat.notification_presentation.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.sowhat.notification_presentation.component.Notification
import com.sowhat.designsystem.component.AppBar
import com.sowhat.designsystem.theme.JustSayItTheme
import com.sowhat.designsystem.R

@Composable
fun NotificationRoute(
    navController: NavController
) {
    NotificationScreen()
}

@Composable
fun NotificationScreen() {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        contentColor = contentColorFor(backgroundColor = JustSayItTheme.Colors.mainBackground),
        topBar = {
            AppBar(
                title = stringResource(id = R.string.appbar_notification),
                navigationIcon = null,
                actionIcon = null,
                bottomBorder = true
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .background(JustSayItTheme.Colors.mainBackground),
        ) {
            items(20) {
                Notification(
                    title = "오늘은 피그마 파일 하나를 공유하려고 합니다...",
                    content = "글에 공감이 추가되었어요.",
                    time = "1분 전",
                    drawable = R.drawable.ic_happy_96,
                    statusText = "행복"
                )
            }
        }
    }
}

@Preview
@Composable
fun NotificationScreenPreview() {
    NotificationScreen()
}