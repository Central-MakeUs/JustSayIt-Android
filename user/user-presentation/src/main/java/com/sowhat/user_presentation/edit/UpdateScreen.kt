package com.sowhat.user_presentation.edit

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sowhat.common.model.UiState
import com.sowhat.common.model.UpdateEvent
import com.sowhat.common.model.UpdateFormEvent
import com.sowhat.common.model.UpdateFormState
import com.sowhat.common.util.LaunchWhenStarted
import com.sowhat.common.util.ObserveEvents
import com.sowhat.common.util.getImageMultipartBody
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
import com.sowhat.designsystem.component.PopupMenuItem
import com.sowhat.designsystem.component.ProfileImage
import com.sowhat.designsystem.theme.Gray200
import com.sowhat.designsystem.theme.Gray400
import com.sowhat.designsystem.theme.JustSayItTheme
import com.sowhat.user_domain.model.UserInfoDomain
import com.sowhat.user_presentation.navigation.navigateUpToSetting
import com.sowhat.user_presentation.setting.SettingViewModel

@Composable
fun UpdateRoute(
    appNavController: NavHostController,
    settingViewModel: SettingViewModel = hiltViewModel(),
    updateViewModel: UpdateViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    val isLoading = settingViewModel.uiState.collectAsState().value.isLoading ||
            updateViewModel.updateInfoUiState.collectAsState().value.isLoading

    val uiState = settingViewModel.uiState.collectAsState().value

    LaunchWhenStarted {
        settingViewModel.getUserInfo()
    }

    LaunchedEffect(key1 = true) {
        updateViewModel.onEvent(
            UpdateFormEvent.ProfileChanged(null)
        )
    }

    ObserveEvents(flow = updateViewModel.updateEvent) { uiEvent ->
        when (uiEvent) {
            is UpdateEvent.NavigateUp -> {
                Log.i("UpdateScreen", "navigate to main")
                appNavController.navigateUpToSetting()
            }
            is UpdateEvent.Error -> {
                Log.i("UpdateScreen", "error ${uiEvent.message}")
            }
        }
    }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            // the returned result from the file picker : Uri
            // to display -> read the uri and convert it into a bitmap -> Coil Image Library
            uri?.let {
                updateViewModel.onEvent(UpdateFormEvent.ProfileChanged(getImageMultipartBody(context, uri, "profileImg", "profile_image")))
            }
            updateViewModel.newImageUri = uri
        }
    )

    val popupMenuItems = listOf(
        PopupMenuItem(
            title =  stringResource(id = R.string.dropdown_upload_image),
            onItemClick = {
                imagePicker.launch("image/*")
            }
        ),
        PopupMenuItem(
            title =  stringResource(id = R.string.dropdown_default_image),
            onItemClick = {
                updateViewModel.onEvent(UpdateFormEvent.ProfileChanged(null))
                updateViewModel.newImageUri = null
            }
        )
    )

    UpdateScreen(
        appNavController = appNavController,
        uiState = uiState,
        isLoading = isLoading,
        isValid = updateViewModel.isValid,
        formState = updateViewModel.formState,
        newProfileUri = updateViewModel.newImageUri,
        dropdownVisible = updateViewModel.dropdown,
        dropdownMenuItems = popupMenuItems,
        onSubmit = updateViewModel::updateUserInfo,
        onEvent = updateViewModel::onEvent,
        onProfileClick = {
            updateViewModel.dropdown = true
        },
        onDropdownDismiss = {
            updateViewModel.dropdown = false
        }
    )
}

@Composable
fun UpdateScreen(
    modifier: Modifier = Modifier,
    appNavController: NavHostController,
    isLoading: Boolean,
    uiState: UiState<UserInfoDomain>,
    formState: UpdateFormState,
    isValid: Boolean,
    onProfileClick: () -> Unit,
    newProfileUri: Uri?,
    dropdownVisible: Boolean,
    dropdownMenuItems: List<PopupMenuItem>,
    onDropdownDismiss: () -> Unit,
    onSubmit: () -> Unit,
    onEvent: (UpdateFormEvent) -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            AppBar(
                title = PROFILE_SETTING,
                navigationIcon = R.drawable.ic_back_24,
                actionText = COMPLETE,
                isValid = isValid,
                onActionTextClick = onSubmit,
                onNavigationIconClick = {
                    appNavController.navigateUpToSetting()
                }
            )
        }
    ) { paddingValues ->

        EditScreenContent(
            modifier = Modifier.padding(paddingValues),
            onProfileClick = onProfileClick,
            newProfileUri = newProfileUri,
            dropdownVisible = dropdownVisible,
            dropdownMenuItems = dropdownMenuItems,
            onDropdownDismiss = onDropdownDismiss,
            formState = formState,
            uiState = uiState,
            onEvent = onEvent
        )

        if (isLoading) CenteredCircularProgress()
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditScreenContent(
    modifier: Modifier = Modifier,
    onProfileClick: () -> Unit,
    newProfileUri: Uri?,
    dropdownVisible: Boolean,
    dropdownMenuItems: List<PopupMenuItem>,
    onDropdownDismiss: () -> Unit,
    formState: UpdateFormState,
    uiState: UiState<UserInfoDomain>,
    onEvent: (UpdateFormEvent) -> Unit
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
            profileUri = newProfileUri ?: uiState.data?.profileInfo?.profileImg,
            badgeDrawable = R.drawable.ic_camera_24,
            badgeBackgroundColor = Gray200,
            badgeIconTint = Gray400,
            onClick = onProfileClick,
            dropdownVisible = dropdownVisible,
            dropdownMenuItems = dropdownMenuItems,
            onDropdownDismiss = onDropdownDismiss,
            onItemClick = { popMenuItem: PopupMenuItem ->
                popMenuItem.onItemClick?.let { it() }
            }
        )

        DefaultTextField(
            modifier = Modifier,
            title = CONFIG_NICKNAME_TITLE,
            placeholder = uiState.data?.profileInfo?.nickname ?: stringResource(id = R.string.error_server),
            onValueChange = { updatedValue ->
                if (updatedValue.length <= 12) {
                    onEvent(UpdateFormEvent.NicknameChanged(updatedValue))
                }
            },
            value = formState.nickname
        )


        Cell(
            title = stringResource(id = R.string.title_gender),
            leadingIcon = null,
            trailingText = uiState.data?.personalInfo?.gender ?: stringResource(id = R.string.error_server)
        )

        Cell(
            title = stringResource(id = R.string.title_dob),
            leadingIcon = null,
            trailingText = uiState.data?.personalInfo?.birth ?: stringResource(id = R.string.error_server)
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

    UpdateScreen(
        isValid = isValid,
        onProfileClick = {},
        newProfileUri = null,
        dropdownVisible = true,
        onDropdownDismiss = {},
        dropdownMenuItems = listOf(

        ),
        onSubmit = {},
        uiState = UiState(),
        formState = UpdateFormState(),
        isLoading = false,
        onEvent = {},
        appNavController = rememberNavController()
    )
}