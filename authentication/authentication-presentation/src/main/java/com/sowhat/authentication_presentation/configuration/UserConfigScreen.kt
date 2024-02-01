package com.sowhat.authentication_presentation.configuration

import android.content.res.Configuration
import android.net.Uri
import android.util.Log
import android.view.ViewTreeObserver
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.sowhat.common.util.ObserveEvents
import com.sowhat.common.model.RegistrationFormEvent
import com.sowhat.common.model.RegistrationFormState
import com.sowhat.common.model.SignUpEvent
import com.sowhat.common.util.getImageMultipartBody
import com.sowhat.designsystem.R
import com.sowhat.designsystem.component.AppBar
import com.sowhat.designsystem.component.DefaultTextField
import com.sowhat.designsystem.theme.Gray200
import com.sowhat.designsystem.theme.Gray400
import com.sowhat.designsystem.common.CONFIG_NICKNAME_PLACEHOLDER
import com.sowhat.designsystem.common.CONFIG_NICKNAME_TITLE
import com.sowhat.designsystem.common.noRippleClickable
import com.sowhat.designsystem.component.CenteredCircularProgress
import com.sowhat.designsystem.component.ProfileImage
import com.sowhat.designsystem.theme.JustSayItTheme
import com.sowhat.authentication_presentation.common.GENDERS
import com.sowhat.authentication_presentation.common.MAX_DAY_LENGTH
import com.sowhat.authentication_presentation.common.MAX_MONTH_LENGTH
import com.sowhat.authentication_presentation.common.MAX_NICKNAME_LENGTH
import com.sowhat.authentication_presentation.common.MAX_YEAR_LENGTH
import com.sowhat.authentication_presentation.common.MIMETYPE_IMAGE
import com.sowhat.authentication_presentation.common.PART_PROFILE_IMG
import com.sowhat.authentication_presentation.common.TextFieldInfo
import com.sowhat.authentication_presentation.common.USER_CONFIG_SCREEN
import com.sowhat.authentication_presentation.component.DescButton
import com.sowhat.authentication_presentation.component.DobTextField
import com.sowhat.authentication_presentation.component.Selection
import com.sowhat.authentication_presentation.navigation.navigateToMain
import com.sowhat.common.navigation.CONFIG_EDIT

@Composable
fun UserConfigRoute(
    viewModel: UserConfigViewModel = hiltViewModel(),
    navController: NavHostController
) {

    val context = LocalContext.current
    val keyboardHeight = WindowInsets.ime.getBottom(LocalDensity.current)

    val uiState = viewModel.uiState.collectAsState().value
    val formState = viewModel.formState

    LaunchedEffect(key1 = true) {
        viewModel.onEvent(
            RegistrationFormEvent.ProfileChanged(null)
        )
    }

    ObserveEvents(flow = viewModel.signUpEvent) { uiEvent ->
        when (uiEvent) {
            is SignUpEvent.NavigateToMain -> {
                Log.i(USER_CONFIG_SCREEN, "navigate to main")
                navController.navigateToMain(popUpTo = CONFIG_EDIT)
            }
            is SignUpEvent.Error -> {
                Log.i(USER_CONFIG_SCREEN, "error ${uiEvent.message}")
            }
        }
    }

    val genders = listOf(stringResource(id = R.string.item_gender_male), stringResource(id = R.string.item_gender_female))

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                val file = getImageMultipartBody(context, it, PART_PROFILE_IMG, "profile_image")
                viewModel.onEvent(RegistrationFormEvent.ProfileChanged(file))
            }
            viewModel.imageUri = uri
        }
    )

    val onReset = {
        viewModel.imageUri = null
        viewModel.onEvent(RegistrationFormEvent.ProfileChanged(null))
    }


    UserConfigScreen(
        modifier = Modifier,
        isLoading = uiState.isLoading,
        isValid = viewModel.isFormValid,
        onProfileClick = {
            imagePicker.launch(MIMETYPE_IMAGE)
        },
        profileUri = viewModel.imageUri,
        formState = formState,
        onEvent = viewModel::onEvent,
        onSubmitClick = viewModel::signUp,
        onReset = onReset
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserConfigScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    isValid: Boolean,
    formState: RegistrationFormState,
    profileUri: Uri?,
    onProfileClick: () -> Unit,
    onSubmitClick: () -> Unit,
    onEvent: (RegistrationFormEvent) -> Unit,
    onReset: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val nameFocusRequester = remember { FocusRequester() }
    val yearFocusRequester = remember { FocusRequester() }
    val monthFocusRequester = remember { FocusRequester() }
    val dayFocusRequester = remember { FocusRequester() }

    val dob = listOf(
        TextFieldInfo(
            focusRequester = Modifier.focusRequester(yearFocusRequester),
            title = stringResource(id = R.string.placeholder_dob_year),
            value = formState.year,
            placeholder = stringResource(id = R.string.placeholder_dob_year),
            onValueChange = { updatedValue ->
                if (updatedValue.length <= MAX_YEAR_LENGTH) {
                    onEvent(RegistrationFormEvent.YearChanged(updatedValue))
                }
                if (updatedValue.length == MAX_YEAR_LENGTH) {
                    monthFocusRequester.requestFocus()
                }
            },
            onNext = {
                monthFocusRequester.requestFocus()
            }
        ),
        TextFieldInfo(
            focusRequester = Modifier.focusRequester(monthFocusRequester),
            title = stringResource(id = R.string.placeholder_dob_month),
            value = formState.month,
            placeholder = stringResource(id = R.string.placeholder_dob_month),
            onValueChange = { updatedValue ->
                if (updatedValue.length <= MAX_MONTH_LENGTH) {
                    onEvent(RegistrationFormEvent.MonthChanged(updatedValue))
                }
                if (updatedValue.length == MAX_MONTH_LENGTH) {
                    dayFocusRequester.requestFocus()
                }
            },
            onNext = { month ->
                if (month.length == 1) {
                    onEvent(RegistrationFormEvent.MonthChanged("0$month"))
                }
                dayFocusRequester.requestFocus()
            }
        ),
        TextFieldInfo(
            focusRequester = Modifier.focusRequester(dayFocusRequester),
            title = stringResource(id = R.string.placeholder_dob_day),
            value = formState.day,
            placeholder = stringResource(id = R.string.placeholder_dob_day),
            onValueChange = { updatedValue ->
                if (updatedValue.length <= MAX_DAY_LENGTH) {
                    onEvent(RegistrationFormEvent.DayChanged(updatedValue))
                }
                if (updatedValue.length == MAX_YEAR_LENGTH) {
                    dayFocusRequester.freeFocus()
                }
            },
            onNext = { day ->
                if (day.length == 1) {
                    onEvent(RegistrationFormEvent.DayChanged("0$day"))
                }
                keyboardController?.hide()
            }
        )
    )

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .noRippleClickable { keyboardController?.hide() }
            .background(JustSayItTheme.Colors.mainBackground),
        topBar = {
            AppBar(
                title = null,
                navigationIcon = null,
                actionText = null,
                isValid = isValid,
                onActionTextClick = {}
            )
        },
        bottomBar = {
            DescButton(
                isValid = isValid,
                onSubmitClick = onSubmitClick
            )
        }
    ) { paddingValues ->

        val imeState = rememberImeState()
        val scrollState = rememberScrollState()

        var isMenuVisible by remember {
            mutableStateOf(false)
        }

        LaunchedEffect(key1 = imeState.value) {
            if (imeState.value) {
                scrollState.animateScrollTo(scrollState.maxValue, tween(300))
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(JustSayItTheme.Colors.mainBackground)
                .verticalScroll(scrollState)
                .padding(paddingValues)
        ) {
            ProfileImage(
                profileUri = profileUri,
                badgeDrawable = R.drawable.ic_camera_24,
                badgeBackgroundColor = Gray200,
                badgeIconTint = Gray400,
                onChooseClick = onProfileClick,
                isMenuVisible = isMenuVisible,
                onMenuShowChange = { isVisible ->
                    isMenuVisible = isVisible
                },
                onResetClick = onReset,
                onMenuDismiss = { isMenuVisible = false }
            )

            DefaultTextField(
                title = CONFIG_NICKNAME_TITLE,
                placeholder = CONFIG_NICKNAME_PLACEHOLDER,
                value = formState.nickname,
                onValueChange = { updatedValue ->
                    if (updatedValue.length <= MAX_NICKNAME_LENGTH) {
                        onEvent(RegistrationFormEvent.NicknameChanged(updatedValue))
                    }
                    if (updatedValue.length == MAX_NICKNAME_LENGTH) {
                        yearFocusRequester.requestFocus()
                    }
                },
                onNext = {
                    yearFocusRequester.requestFocus()
                }
            )

            Selection(
                title = stringResource(id = R.string.title_gender),
                buttons = GENDERS,
                activeButton = formState.gender,
                onClick = { updatedValue ->
                    if (updatedValue in GENDERS) {
                        onEvent(RegistrationFormEvent.GenderChanged(updatedValue))
                    }
                }
            )

            DobTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                title = stringResource(id = R.string.title_dob),
                items = dob,
            )
        }

        if (isLoading) CenteredCircularProgress()
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun UserConfigScreenPreview() {
    var id by rememberSaveable {
        mutableStateOf("")
    }

    var isValid by rememberSaveable {
        mutableStateOf(false)
    }

    UserConfigScreen(
        isValid = isValid,
        onProfileClick = {},
        profileUri = null,
        isLoading = false,
        onSubmitClick = {},
        formState = RegistrationFormState(),
        onEvent = {},
        onReset = {}
    )
}

@Composable
fun rememberImeState(): State<Boolean> {
    val imeState = remember {
        mutableStateOf(false)
    }

    val view = LocalView.current
    DisposableEffect(view) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            val isKeyboardOpen = ViewCompat.getRootWindowInsets(view)
                ?.isVisible(WindowInsetsCompat.Type.ime()) ?: true
            imeState.value = isKeyboardOpen
        }

        view.viewTreeObserver.addOnGlobalLayoutListener(listener)
        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }

    return imeState
}