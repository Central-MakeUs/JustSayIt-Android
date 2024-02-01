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

    LaunchedEffect(key1 = true) {
        updateViewModel.onEvent(UpdateFormEvent.ProfileChanged(null))
    }
    
    LaunchedEffect(key1 = uiState.data?.profileInfo) {
        updateViewModel.onEvent(UpdateFormEvent.InitialProfileUpdated(
            uiState.data?.profileInfo?.profileImg
        ))
        updateViewModel.onEvent(UpdateFormEvent.ProfileUriChanged(
            Uri.parse(uiState.data?.profileInfo?.profileImg ?: return@LaunchedEffect)
                ?: null
        ))
        updateViewModel.onEvent(UpdateFormEvent.NicknamePostDataChanged(
            uiState.data?.profileInfo?.nickname ?: return@LaunchedEffect
        ))
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
                updateViewModel.onEvent(UpdateFormEvent.IsProfileChanged(true))
                updateViewModel.onEvent(UpdateFormEvent.IsProfileDefault(false))
                updateViewModel.onEvent(UpdateFormEvent.ProfileUriChanged(it))
                updateViewModel.onEvent(UpdateFormEvent.ProfileChanged(getImageMultipartBody(context, uri, "profileImg", "profile_image")))
            }
        }
    )

    UpdateScreen(
        appNavController = appNavController,
        uiState = uiState,
        isLoading = isLoading,
        isValid = updateViewModel.isValid,
        formState = updateViewModel.formState,
        onSubmit = updateViewModel::updateUserInfo,
        onEvent = updateViewModel::onEvent,
        onProfileClick = {
            imagePicker.launch("image/*")
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
            profileUri = formState.newImageUri,
            badgeDrawable = R.drawable.ic_camera_24,
            badgeBackgroundColor = Gray200,
            badgeIconTint = Gray400,
            onChooseClick = onProfileClick,
            isMenuVisible = formState.isPopupMenuVisible,
            onMenuDismiss = {
                onEvent(UpdateFormEvent.DropdownVisibilityChanged(false))
            },
            onResetClick = {
                onEvent(UpdateFormEvent.DropdownVisibilityChanged(false))
                onEvent(UpdateFormEvent.ProfileChanged(null))
                onEvent(UpdateFormEvent.ProfileUriChanged(null))
                onEvent(UpdateFormEvent.IsProfileDefault(true))
                onEvent(UpdateFormEvent.IsProfileChanged(true))
            },
            onMenuShowChange = { isVisible ->
                onEvent(UpdateFormEvent.DropdownVisibilityChanged(isVisible))
            }
        )

        DefaultTextField(
            modifier = Modifier,
            title = CONFIG_NICKNAME_TITLE,
            placeholder = uiState.data?.profileInfo?.nickname ?: stringResource(id = R.string.error_server),
            onValueChange = { updatedValue ->
                if (updatedValue.length <= 12 && updatedValue != uiState.data?.profileInfo?.nickname) {
                    onEvent(UpdateFormEvent.NicknameChanged(updatedValue))
                    onEvent(UpdateFormEvent.NicknamePostDataChanged(updatedValue))
                    onEvent(UpdateFormEvent.IsNameChanged(true))
                } else if (updatedValue.length <= 12 && updatedValue == uiState.data?.profileInfo?.nickname) {
                    onEvent(UpdateFormEvent.NicknameChanged(updatedValue))
                    onEvent(UpdateFormEvent.NicknamePostDataChanged(updatedValue))
                    onEvent(UpdateFormEvent.IsNameChanged(false))
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
        onSubmit = {},
        uiState = UiState(),
        formState = UpdateFormState(),
        isLoading = false,
        onEvent = {},
        appNavController = rememberNavController()
    )
}