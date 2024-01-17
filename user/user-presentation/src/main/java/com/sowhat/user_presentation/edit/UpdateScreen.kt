package com.sowhat.user_presentation.edit

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.sowhat.common.model.UpdateFormEvent
import com.sowhat.common.util.getFile
import com.sowhat.designsystem.R
import com.sowhat.designsystem.common.COMPLETE
import com.sowhat.designsystem.common.CONFIG_NICKNAME_TITLE
import com.sowhat.designsystem.common.DropdownItem
import com.sowhat.designsystem.common.PROFILE_SETTING
import com.sowhat.designsystem.common.noRippleClickable
import com.sowhat.designsystem.component.AppBar
import com.sowhat.designsystem.component.Cell
import com.sowhat.designsystem.component.CenteredCircularProgress
import com.sowhat.designsystem.component.DefaultTextField
import com.sowhat.designsystem.component.ProfileImage
import com.sowhat.designsystem.theme.Gray200
import com.sowhat.designsystem.theme.Gray400
import com.sowhat.designsystem.theme.JustSayItTheme
import com.sowhat.user_presentation.setting.SettingViewModel

@Composable
fun UpdateRoute(
    settingViewModel: SettingViewModel = hiltViewModel(),
    updateViewModel: UpdateViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    val isLoading = settingViewModel.uiState.collectAsState().value.isLoading ||
            updateViewModel.updateInfoUiState.collectAsState().value.isLoading

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            // the returned result from the file picker : Uri
            // to display -> read the uri and convert it into a bitmap -> Coil Image Library
            uri?.let {
                updateViewModel.onEvent(UpdateFormEvent.ProfileChanged(getFile(context, uri, "profileImg")))
            }
            updateViewModel.newImageUri = uri
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
                updateViewModel.onEvent(UpdateFormEvent.ProfileChanged(null))
                updateViewModel.newImageUri = null
            }
        )
    )

    val maxLength = 12

    UpdateScreen(
        isLoading = isLoading,
        isValid = updateViewModel.isValid,
        userName = updateViewModel.formState.nickname,
        profileUrl = settingViewModel.uiState.collectAsState().value.data?.profileInfo?.profileImg ?: "",
        onProfileClick = {
//            imagePicker.launch("image/*")
            updateViewModel.dropdown = true
        },
        newProfileUri = updateViewModel.newImageUri,
        newUserName = updateViewModel.formState.nickname,
        onUserNameChange = { newName ->
            if (newName.length <= maxLength) updateViewModel.onEvent(UpdateFormEvent.NicknameChanged(newName))
        },
        dropdownVisible = updateViewModel.dropdown,
        dropdownMenuItems = dropdownMenuItems,
        onDropdownDismiss = { updateViewModel.dropdown = false },
        onSubmit = updateViewModel::updateUserInfo
    )
}

@Composable
fun UpdateScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    isValid: Boolean,
    userName: String,
    profileUrl: String,
    onProfileClick: () -> Unit,
    newProfileUri: Uri?,
    onUserNameChange: (String) -> Unit,
    newUserName: String,
    dropdownVisible: Boolean,
    dropdownMenuItems: List<DropdownItem>,
    onDropdownDismiss: () -> Unit,
    onSubmit: () -> Unit
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
            onDropdownDismiss = onDropdownDismiss,
            gender = "남",
            dob = "1998.11.23",
            onSubmit = onSubmit
        )

        if (isLoading) CenteredCircularProgress()
    }
}

@OptIn(ExperimentalComposeUiApi::class)
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
    onDropdownDismiss: () -> Unit,
    gender: String,
    dob: String,
    onSubmit: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(JustSayItTheme.Colors.mainBackground)
            .noRippleClickable { keyboardController?.hide() }
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
            onDropdownDismiss = onDropdownDismiss,
            onItemClick = { dropdownItem ->

            }
        )

        DefaultTextField(
            modifier = Modifier,
            title = CONFIG_NICKNAME_TITLE,
            placeholder = userName,
            value = newUserName,
            onValueChange = onUserNameChange
        )

        Cell(title = stringResource(id = R.string.title_gender), leadingIcon = null, trailingText = gender)

        Cell(title = stringResource(id = R.string.title_dob), leadingIcon = null, trailingText = dob)
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

    UpdateScreen(
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
        ),
        isLoading = false,
        onSubmit = {}
    )
}