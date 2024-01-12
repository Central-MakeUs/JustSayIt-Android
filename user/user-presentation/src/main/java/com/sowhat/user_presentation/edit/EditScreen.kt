package com.sowhat.user_presentation.edit

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.sowhat.designsystem.R
import com.sowhat.designsystem.common.COMPLETE
import com.sowhat.designsystem.common.CONFIG_ID_TITLE
import com.sowhat.designsystem.common.DropdownItem
import com.sowhat.designsystem.common.PROFILE_SETTING
import com.sowhat.designsystem.component.AppBar
import com.sowhat.designsystem.component.DefaultTextField
import com.sowhat.designsystem.component.ProfileImage
import com.sowhat.designsystem.theme.Gray200
import com.sowhat.designsystem.theme.Gray400

@Composable
fun EditRoute(
    viewModel: EditViewModel = hiltViewModel()
) {
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            // the returned result from the file picker : Uri
            // to display -> read the uri and convert it into a bitmap -> Coil Image Library
            viewModel.hasImage = uri != null
            viewModel.newImageUri = uri
        }
    )

    val dropdownMenuItems = listOf(
        DropdownItem(
            title = "사진 업로드하기",
            onItemClick = {
                imagePicker.launch("image/*")
            }
        ),
        DropdownItem(
            title = "기본 이미지로 변경",
            onItemClick = {
                viewModel.hasImage = false
                viewModel.newImageUri = null
            }
        )
    )

    val maxLength = 12

    EditScreen(
        isValid = viewModel.isValid,
        userName = viewModel.userName,
        profileUrl = viewModel.profileUrl,
        onProfileClick = {
//            imagePicker.launch("image/*")
            viewModel.dropdown = true
        },
        newProfileUri = viewModel.newImageUri,
        newUserName = viewModel.newUserName,
        onUserNameChange = { newName ->
            if (newName.length <= maxLength) viewModel.newUserName = newName
        },
        dropdownVisible = viewModel.dropdown,
        dropdownMenuItems = dropdownMenuItems,
        onDropdownDismiss = { viewModel.dropdown = false }
    )
}

@Composable
fun EditScreen(
    modifier: Modifier = Modifier,
    isValid: Boolean,
    userName: String,
    profileUrl: String,
    onProfileClick: () -> Unit,
    newProfileUri: Uri?,
    onUserNameChange: (String) -> Unit,
    newUserName: String,
    dropdownVisible: Boolean,
    dropdownMenuItems: List<DropdownItem>,
    onDropdownDismiss: () -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            AppBar(
                title = PROFILE_SETTING,
                navigationIcon = R.drawable.ic_back_24,
                actionText = COMPLETE,
                isValid = isValid
            )
        }
    ) { paddingValues ->
        EditScreenContent(
            modifier = Modifier.padding(paddingValues),
            userName = userName,
            profileUrl = profileUrl,
            onProfileClick = onProfileClick,
            newProfileUri = newProfileUri,
            onUserNameChange = onUserNameChange,
            newUserName = newUserName,
            dropdownVisible = dropdownVisible,
            dropdownMenuItems = dropdownMenuItems,
            onDropdownDismiss = onDropdownDismiss
        )
    }
}

@Composable
fun EditScreenContent(
    modifier: Modifier = Modifier,
    userName: String,
    profileUrl: String,
    onProfileClick: () -> Unit,
    newProfileUri: Uri?,
    newUserName: String,
    onUserNameChange: (String) -> Unit,
    dropdownVisible: Boolean,
    dropdownMenuItems: List<DropdownItem>,
    onDropdownDismiss: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        ProfileImage(
            modifier = Modifier,
            profileUri = newProfileUri ?: profileUrl,
            badgeDrawable = R.drawable.ic_camera_24,
            badgeBackgroundColor = Gray200,
            badgeIconTint = Gray400,
            onClick = onProfileClick,
            dropdownVisible = dropdownVisible,
            dropdownMenuItems = dropdownMenuItems,
            onDropdownDismiss = onDropdownDismiss
        )

        DefaultTextField(
            modifier = Modifier,
            title = CONFIG_ID_TITLE,
            placeholder = userName,
            value = newUserName,
            onValueChange = onUserNameChange
        )
    }
}

@Preview
@Composable
fun EditScreenPreview() {

    var newUserName by remember {
        mutableStateOf("")
    }

    val profileUrl by remember {
        mutableStateOf("https://github.com/kmkim2689/Android-Wiki/assets/101035437/88d7b249-ad72-4be9-8d79-38dc942e0a7f")
    }

    val isValid by remember {
        derivedStateOf {
            newUserName.length in (2..12)
        }
    }

    EditScreen(
        isValid = isValid,
        userName = "케이엠",
        profileUrl = profileUrl,
        onProfileClick = {},
        newProfileUri = null,
        newUserName = newUserName,
        onUserNameChange = { newName ->
            newUserName = newName
        },
        dropdownVisible = true,
        onDropdownDismiss = {},
        dropdownMenuItems = listOf(
            DropdownItem(
                title = "사진 업로드하기",
                onItemClick = {

                }
            ),
            DropdownItem(
                title = "기본 이미지로 변경",
                onItemClick = {

                }
            )
        )
    )
}