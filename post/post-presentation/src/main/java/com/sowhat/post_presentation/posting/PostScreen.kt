package com.sowhat.post_presentation.posting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sowhat.designsystem.component.AppBar
import com.sowhat.designsystem.component.DefaultButtonFull
import com.sowhat.post_presentation.R
import com.sowhat.post_presentation.common.SubjectItem
import com.sowhat.post_presentation.component.CurrentMoodSelection
import com.sowhat.post_presentation.navigation.navigateBack

@Composable
fun PostRoute(
    navController: NavController,
    viewModel: PostViewModel = hiltViewModel()
) {
    PostScreen(navController = navController)
}

@Composable
fun PostScreen(
    navController: NavController
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppBar(
                title = stringResource(id = com.sowhat.designsystem.R.string.appbar_post),
                navigationIcon = com.sowhat.designsystem.R.drawable.ic_back_24,
                actionIcon = null,
                onNavigationIconClick = {
                    // TODO 이전에 저장 안된다는 문구 필요
                    navController.navigateBack()
                }
            )
        },
        bottomBar = {
            DefaultButtonFull(
                text = stringResource(com.sowhat.designsystem.R.string.button_post),
                isActive = false,
                onClick = {

                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
//            CurrentMoodSelection(subjectItem = SubjectItem(stringResource(id = com.sowhat.designsystem.R.string.title_select_mood), stringResource(
//                id = com.sowhat.designsystem.R.string.subtitle_select_mood
//            )), currentMood = , onChange = , moodItems = )
        }
    }
}