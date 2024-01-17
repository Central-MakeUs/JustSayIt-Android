package com.sowhat.presentation.configuration

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.view.ViewTreeObserver
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
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
import androidx.navigation.compose.rememberNavController
import com.sowhat.common.util.ObserveEvents
import com.sowhat.common.model.RegistrationFormEvent
import com.sowhat.common.model.SignUpEvent
import com.sowhat.common.util.getFile
import com.sowhat.designsystem.R
import com.sowhat.designsystem.component.AppBar
import com.sowhat.designsystem.component.DefaultTextField
import com.sowhat.designsystem.theme.Gray200
import com.sowhat.designsystem.theme.Gray400
import com.sowhat.designsystem.common.CONFIG_NICKNAME_PLACEHOLDER
import com.sowhat.designsystem.common.CONFIG_NICKNAME_TITLE
import com.sowhat.designsystem.common.noRippleClickable
import com.sowhat.designsystem.component.CenteredCircularProgress
import com.sowhat.designsystem.component.DefaultButtonFull
import com.sowhat.designsystem.component.ProfileImage
import com.sowhat.designsystem.theme.Gray500
import com.sowhat.designsystem.theme.JustSayItTheme
import com.sowhat.presentation.common.TextFieldInfo
import com.sowhat.presentation.component.DobTextField
import com.sowhat.presentation.component.Selection
import com.sowhat.presentation.navigation.navigateToMain
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File

@Composable
fun UserConfigRoute(
    viewModel: UserConfigViewModel = hiltViewModel(),
    navController: NavHostController
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val keyboardHeight = WindowInsets.ime.getBottom(LocalDensity.current)
    val maxLength = 12

    val isLoading = viewModel.uiState.collectAsState().value.isLoading

    ObserveEvents(flow = viewModel.signUpEvent) { uiEvent ->
        when (uiEvent) {
            is SignUpEvent.NavigateToMain -> {
                Log.i("UserConfigScreen", "navigate to main")
                navController.navigateToMain()
            }
            is SignUpEvent.Error -> {
                Log.i("UserConfigScreen", "error ${uiEvent.message}")
            }
        }
    }

    val genders = listOf(
        stringResource(id = R.string.item_gender_male),
        stringResource(id = R.string.item_gender_female)
    )

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                viewModel.onEvent(
                    RegistrationFormEvent.ProfileChanged(getFile(context, it, "profileImg"))
                )
            }

            viewModel.imageUri = uri
        }
    )



    val dob = listOf(
        TextFieldInfo(
            title = stringResource(id = R.string.placeholder_dob_year),
            value = viewModel.formState.year,
            placeholder = stringResource(id = R.string.placeholder_dob_year),
            onValueChange = { changed ->
                viewModel.onEvent(RegistrationFormEvent.YearChanged(changed))
            }
        ),
        TextFieldInfo(
            title = stringResource(id = R.string.placeholder_dob_month),
            value = viewModel.formState.month,
            placeholder = stringResource(id = R.string.placeholder_dob_month),
            onValueChange = { changed ->
                viewModel.onEvent(RegistrationFormEvent.MonthChanged(changed))
            }
        ),
        TextFieldInfo(
            title = stringResource(id = R.string.placeholder_dob_day),
            value = viewModel.formState.day,
            placeholder = stringResource(id = R.string.placeholder_dob_day),
            onValueChange = { changed ->
                viewModel.onEvent(RegistrationFormEvent.DayChanged(changed))
            }
        )
    )

    UserConfigScreen(
        modifier = Modifier,
        isLoading = isLoading,
        nickname = viewModel.formState.nickname,
        navController = navController,
        isValid = viewModel.isFormValid,
        onNicknameChange = { changedId ->
            if (changedId.length <= maxLength) viewModel.onEvent(RegistrationFormEvent.NicknameChanged(changedId))
        },
        onProfileClick = {
            imagePicker.launch("image/*")
        },
        profileUri = viewModel.imageUri,
        genders = genders,
        onGenderChange = { changedGender ->
            viewModel.onEvent(RegistrationFormEvent.GenderChanged(changedGender))
        },
        dobItems = dob,
        currentGender = viewModel.formState.gender,
        onSubmitClick = viewModel::signUp
    )
}


fun getMultipart(appContext: Context, uri: Uri): MultipartBody.Part {
//    val cacheDir = appContext.cacheDir
//    val file = File(cacheDir, "profile_image.jpeg")
//    val inputStream = appContext.contentResolver.openInputStream(uri)
//    val outputStream = FileOutputStream(file)
//    inputStream!!.copyTo(outputStream)
//
//    val requestBody: RequestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
//    val part = MultipartBody.Part.createFormData("profile", file.name, requestBody)
//
//    inputStream.close()
//
//    return part
    val file: File? = uri.path?.let { File(it) }
    val inputStream = appContext.contentResolver?.openInputStream(uri)
    val bitmap = BitmapFactory.decodeStream(inputStream)
    val byteOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteOutputStream)
    val requestBody = byteOutputStream.toByteArray().toRequestBody("image/jpeg".toMediaTypeOrNull())
    Log.e("UserConfigScreen","${requestBody}.jpeg")
    return MultipartBody.Part.createFormData("profileImg", "${file?.name}.jpeg", requestBody)
}

@OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun UserConfigScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    navController: NavHostController,
    nickname: String,
    isValid: Boolean,
    profileUri: Uri?,
    currentGender: String,
    onNicknameChange: (String) -> Unit,
    onProfileClick: () -> Unit,
    genders: List<String>,
    onGenderChange: (String) -> Unit,
    dobItems: List<TextFieldInfo>,
    onSubmitClick: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .noRippleClickable { keyboardController?.hide() },
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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.basicMarquee(),
                    text = stringResource(id = R.string.desc_info_immutable),
                    style = JustSayItTheme.Typography.detail1,
                    color = Gray500,
                    maxLines = 1
                )

                DefaultButtonFull(
                    modifier = Modifier
                        .padding(
                            top = JustSayItTheme.Spacing.spaceSmall,
                            bottom = JustSayItTheme.Spacing.spaceLarge,
                            start = JustSayItTheme.Spacing.spaceLarge,
                            end = JustSayItTheme.Spacing.spaceLarge,
                        ),
                    text = stringResource(id = R.string.button_start),
                    isActive = isValid,
                    onClick = onSubmitClick
                )
            }
        }
    ) { paddingValues ->

        val imeState = rememberImeState()
        val scrollState = rememberScrollState()

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
                onClick = onProfileClick
            )

            DefaultTextField(
                title = CONFIG_NICKNAME_TITLE,
                placeholder = CONFIG_NICKNAME_PLACEHOLDER,
                value = nickname,
                onValueChange = onNicknameChange
            )

            Selection(
                title = stringResource(id = R.string.title_gender),
                buttons = genders,
                activeButton = currentGender,
                onClick = onGenderChange
            )

            DobTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                title = stringResource(id = R.string.title_dob),
                items = dobItems,
            )
        }

        if (isLoading) CenteredCircularProgress()
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

    val navController = rememberNavController()

    UserConfigScreen(
        nickname = id,
        onNicknameChange = { changedId ->
            id = if (changedId.length <= 12) changedId else id
            isValid = id.length in (2..12)
        },
        isValid = isValid,
        onProfileClick = {},
        profileUri = null,
        navController = navController,
        genders = listOf("남", "여"),
        onGenderChange = {},
        isLoading = false,
        dobItems = listOf(
            TextFieldInfo(
                title = stringResource(id = R.string.placeholder_dob_year),
                value = "year",
                placeholder = stringResource(id = R.string.placeholder_dob_year),
                onValueChange = {  }
            ),
            TextFieldInfo(
                title = stringResource(id = R.string.placeholder_dob_month),
                value = "month",
                placeholder = stringResource(id = R.string.placeholder_dob_month),
                onValueChange = {  }
            ),
            TextFieldInfo(
                title = stringResource(id = R.string.placeholder_dob_day),
                value = "day",
                placeholder = stringResource(id = R.string.placeholder_dob_day),
                onValueChange = {  }
            )
        ),
        currentGender = "남",
        onSubmitClick = {}
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