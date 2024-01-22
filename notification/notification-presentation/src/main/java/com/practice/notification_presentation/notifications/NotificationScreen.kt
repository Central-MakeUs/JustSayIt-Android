package com.practice.notification_presentation.notifications

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun NotificationRoute(
    navController: NavController
) {
    NotificationScreen()
}

@Composable
fun NotificationScreen() {
    Scaffold { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues))
    }
}