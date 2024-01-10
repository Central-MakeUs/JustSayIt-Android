package com.sowhat.presentation.configuration

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.sowhat.designsystem.component.AppBar
import com.sowhat.designsystem.component.DefaultTextField
import com.sowhat.designsystem.theme.Gray200
import com.sowhat.designsystem.theme.Gray400
import com.sowhat.presentation.common.COMPLETE
import com.sowhat.presentation.common.CONFIG_ID_PLACEHOLDER
import com.sowhat.presentation.common.CONFIG_ID_TITLE
import com.sowhat.presentation.component.ProfileImage

@Composable
fun UserConfigRoute(
    viewModel: UserConfigViewModel = hiltViewModel()
) {
    UserConfigScreen(
        id = viewModel.id,
        isValid = viewModel.isValid,
        onIdChange = { changedId ->
            viewModel.id = changedId
        }
    )
}

@Composable
fun UserConfigScreen(
    modifier: Modifier = Modifier,
    id: String,
    isValid: Boolean,
    onIdChange: (String) -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            AppBar(
                title = null,
                navigationIcon = null,
                actionText = COMPLETE,
                isValid = isValid
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            ProfileImage(
                profileUri = "https://github.com/kmkim2689/Android-Wiki/assets/101035437/88d7b249-ad72-4be9-8d79-38dc942e0a7f",
                badgeDrawable = com.sowhat.designsystem.R.drawable.ic_camera_24,
                badgeBackgroundColor = Gray200,
                badgeIconTint = Gray400,
                onClick = {

                }
            )

            DefaultTextField(
                title = CONFIG_ID_TITLE,
                placeholder = CONFIG_ID_PLACEHOLDER,
                value = id,
                onValueChange = onIdChange
            )
        }
    }
}

@Preview
@Composable
fun UserConfigScreenPreview() {
    var id by rememberSaveable {
        mutableStateOf("")
    }

    var isValid by rememberSaveable {
        mutableStateOf(false)
    }

    UserConfigScreen(
        id = id,
        onIdChange = { changedId ->
            id = changedId
            isValid = id.length in (2..12)
        },
        isValid = isValid
    )
}