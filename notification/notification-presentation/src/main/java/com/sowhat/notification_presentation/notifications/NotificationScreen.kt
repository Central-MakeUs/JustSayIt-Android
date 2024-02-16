package com.sowhat.notification_presentation.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sowhat.common.util.LaunchWhenStarted
import com.sowhat.notification_presentation.component.Notification
import com.sowhat.designsystem.component.AppBar
import com.sowhat.designsystem.theme.JustSayItTheme
import com.sowhat.designsystem.R
import com.sowhat.designsystem.component.NoItemNotice
import com.sowhat.notification_presentation.common.NotificationUiState

@Composable
fun NotificationRoute(
    navController: NavController,
    viewModel: NotificationViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value

    NotificationScreen(
        uiState = uiState
    )

    LaunchWhenStarted {
        viewModel.getNotificationData()
    }
}

@Composable
fun NotificationScreen(uiState: NotificationUiState) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(JustSayItTheme.Colors.mainBackground),
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
                .fillMaxSize()
                .padding(paddingValues)
                .background(JustSayItTheme.Colors.mainBackground),
        ) {
            items(
                items = uiState.notifications.reversed(),
                key = { item -> item.notificationUUID }
            ) {
                Notification(
                    title = it.title,
                    content = it.body,
                    time = it.date ?: "방금 전",
                    drawable = R.drawable.ic_notification_event,
                    statusText = null
                )
            }
        }

        if (uiState.notifications.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(bottom = JustSayItTheme.Spacing.spaceXL),
                contentAlignment = Alignment.Center
            ) {
                NoItemNotice(
                    painter = painterResource(id = R.drawable.ic_notification_default_24),
                    text = stringResource(id = R.string.notice_no_notification)
                )
            }
        }
    }
}

@Preview
@Composable
fun NotificationScreenPreview() {
    NotificationScreen(uiState = NotificationUiState())
}