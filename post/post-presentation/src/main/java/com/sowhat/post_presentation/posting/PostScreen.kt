package com.sowhat.post_presentation.posting

import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat.startForegroundService
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sowhat.common.model.PostingEvent
import com.sowhat.common.model.UiState
import com.sowhat.common.model.UploadProgress
import com.sowhat.common.util.ObserveEvents
import com.sowhat.designsystem.R
import com.sowhat.designsystem.common.MoodItem
import com.sowhat.designsystem.common.addFocusCleaner
import com.sowhat.designsystem.common.rememberMoodItems
import com.sowhat.designsystem.component.AlertDialog
import com.sowhat.designsystem.component.AppBar
import com.sowhat.designsystem.component.CenteredCircularProgress
import com.sowhat.designsystem.component.DefaultButtonFull
import com.sowhat.designsystem.theme.JustSayItTheme
import com.sowhat.post_presentation.common.PostFormEvent
import com.sowhat.post_presentation.common.PostFormState
import com.sowhat.post_presentation.common.SubjectItem
import com.sowhat.post_presentation.component.CurrentMoodSelection
import com.sowhat.post_presentation.component.ImageSelection
import com.sowhat.post_presentation.component.PostText
import com.sowhat.post_presentation.component.PostToggle
import com.sowhat.post_presentation.component.SympathySelection
import com.sowhat.post_presentation.navigation.navigateBack
import com.sowhat.post_presentation.util.PostProgressService
import com.sowhat.post_presentation.util.PostProgressService.Companion.ANONYMOUS
import com.sowhat.post_presentation.util.PostProgressService.Companion.EMOTION
import com.sowhat.post_presentation.util.PostProgressService.Companion.EMPATHY_LIST
import com.sowhat.post_presentation.util.PostProgressService.Companion.IMAGE_URIS
import com.sowhat.post_presentation.util.PostProgressService.Companion.OPENED
import com.sowhat.post_presentation.util.PostProgressService.Companion.POST_TEXT


@Composable
fun PostRoute(
    navController: NavController,
    viewModel: PostViewModel = hiltViewModel()
) {
    val formState = viewModel.formState
    val uiState = viewModel.uiState
    val moods = rememberMoodItems()
    val context = LocalContext.current
    val availableImageCount = 4

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uri ->
            uri.let {
                val currentImages = formState.images.toMutableList()
                val currentSize = currentImages.size
                val availableAddCount = availableImageCount - currentSize
                it.forEachIndexed { index, uri ->
                    if (index + 1 <= availableAddCount) currentImages.add(uri)
                }

                viewModel.onEvent(PostFormEvent.ImageListUpdated(currentImages))
            }
        }
    )

    ObserveEvents(flow = viewModel.postingEvent) { uiEvent ->
        when (uiEvent) {
            is PostingEvent.NavigateUp -> {
                Log.i("PostScreen", "navigate to main")
                navController.navigateBack()
            }
            is PostingEvent.Error -> {
                Log.i("PostScreen", "error ${uiEvent.message}")
            }
        }
    }

    PostScreen(
        navController = navController,
        formState = formState,
        uiState = uiState,
        isValid = viewModel.isFormValid,
        onEvent = viewModel::onEvent,
        onSubmit = {
            if (viewModel.isFormValid) {
                val intent = Intent(context.applicationContext, PostProgressService::class.java).apply {
                    putExtra(ANONYMOUS, formState.isAnonymous)
                    putExtra(POST_TEXT, formState.postText)
                    putExtra(OPENED, formState.isOpened)
                    putExtra(EMOTION, formState.currentMood?.postData)
                    putExtra(EMPATHY_LIST, formState.sympathyMoodItems.map { it.postData }.toTypedArray())
                    putExtra(IMAGE_URIS, formState.images.map { it.toString() }.toTypedArray())
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(context, intent)
                } else {
                    context.startService(intent)
                }

                navController.navigateBack()
            }

        },
        onAddImage = {
            imagePicker.launch("image/*")
        },
        moods = moods
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PostScreen(
    navController: NavController,
    isValid: Boolean,
    formState: PostFormState,
    uiState: UiState<Unit?>,
    moods: List<MoodItem>,
    onAddImage: () -> Unit,
    onEvent: (PostFormEvent) -> Unit,
    onSubmit: () -> Unit
) {
    val textFocusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    BackHandler(
        enabled = true
    ) {
        if (!formState.isDialogVisible) {
            onEvent(PostFormEvent.DialogVisibilityChanged(true))
        } else {
            onEvent(PostFormEvent.DialogVisibilityChanged(false))
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .addFocusCleaner(focusManager),
        topBar = {
            AppBar(
                title = stringResource(id = R.string.appbar_post),
                navigationIcon = R.drawable.ic_close_24,
                actionIcon = null,
                onNavigationIconClick = {
                    onEvent(PostFormEvent.DialogVisibilityChanged(true))
                }
            )
        },
        bottomBar = {
            Box(modifier = Modifier
                .fillMaxWidth()
                .background(JustSayItTheme.Colors.mainBackground)
            ) {
                DefaultButtonFull(
                    modifier = Modifier
                        .padding(
                            JustSayItTheme.Spacing.spaceBase
                        ),
                    text = stringResource(com.sowhat.designsystem.R.string.button_post),
                    isActive = isValid,
                    onClick = {
                        UploadProgress.current = 0
                        UploadProgress.max = 0
                        onSubmit()
                    }
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .background(JustSayItTheme.Colors.mainBackground)
        ) {
            item {
                CurrentMoodSelection(
                    subjectItem = SubjectItem(
                        stringResource(id = com.sowhat.designsystem.R.string.title_select_mood),
                        stringResource(id = com.sowhat.designsystem.R.string.subtitle_select_mood)
                    ),
                    currentMood = formState.currentMood,
                    onChange = { changedMood ->
                        onEvent(PostFormEvent.CurrentMoodChanged(changedMood))
                    },
                    moodItems = moods
                )
            }

            item {
                PostText(
                    modifier = Modifier,
                    focusRequester = textFocusRequester,
                    subject = SubjectItem(
                        title = stringResource(id = com.sowhat.designsystem.R.string.title_write),
                        subTitle = stringResource(id = com.sowhat.designsystem.R.string.subtitle_write)
                    ),
                    placeholder = stringResource(id = com.sowhat.designsystem.R.string.placeholder_post),
                    text = formState.postText,
                    onTextChange = { changedText ->
                        onEvent(PostFormEvent.PostTextChanged(changedText))
                    },
                    maxLength = 300
                )
            }

            item {
                ImageSelection(
                    images = formState.images,
                    onAddClick = { onAddImage() },
                    onImagesChange = { images ->
                        onEvent(PostFormEvent.ImageListUpdated(images))
                    }
                )
            }

            item {
                PostToggle(
                    subject = SubjectItem(
                        stringResource(id = com.sowhat.designsystem.R.string.title_is_open),
                        stringResource(
                            id = com.sowhat.designsystem.R.string.subtitle_is_open
                        )
                    ),
                    isActivated = true,
                    isChecked = formState.isOpened,
                    onCheckedChange = { updatedState ->
                        onEvent(PostFormEvent.OpenChanged(updatedState))
                    }
                )
            }

            item {
                PostToggle(
                    subject = SubjectItem(
                        stringResource(id = com.sowhat.designsystem.R.string.title_is_anonymous),
                        stringResource(id = com.sowhat.designsystem.R.string.subtitle_is_anonymous)
                    ),
                    isActivated = formState.isOpened,
                    isChecked = formState.isAnonymous,
                    onCheckedChange = { updatedState ->
                        onEvent(PostFormEvent.AnonymousChanged(updatedState))
                    }
                )
            }

            item {
                SympathySelection(
                    isActive = formState.isOpened,
                    subjectItem = SubjectItem(
                        stringResource(id = com.sowhat.designsystem.R.string.title_select_sympathy),
                        stringResource(
                            id = com.sowhat.designsystem.R.string.subtitle_select_sympathy
                        )
                    ),
                    onClick = { changedItem ->
                        val currentItems = formState.sympathyMoodItems.toMutableList()
                        if (changedItem in currentItems) {
                            currentItems.remove(changedItem)
                        } else {
                            currentItems.add(changedItem)
                        }
                        onEvent(PostFormEvent.SympathyItemsChanged(currentItems))
                    },
                    selectedMoods = formState.sympathyMoodItems,
                    moodItems = moods
                )
            }
            
            item {
                Spacer(modifier = Modifier.height(JustSayItTheme.Spacing.spaceLg))
            }
        }

        if (formState.isDialogVisible) {
            AlertDialog(
                title = stringResource(id = com.sowhat.designsystem.R.string.dialog_title_posting),
                subTitle = stringResource(
                    id = com.sowhat.designsystem.R.string.dialog_subtitle_posting
                ),
                buttonContent = stringResource(id = com.sowhat.designsystem.R.string.dialog_button_continue) to stringResource(
                    id = com.sowhat.designsystem.R.string.dialog_button_stop
                ),
                onAccept = {
                    onEvent(PostFormEvent.DialogVisibilityChanged(false))
                    navController.navigateBack()
                },
                onDismiss = { onEvent(PostFormEvent.DialogVisibilityChanged(false)) }
            )
        }
    }

    if (uiState.isLoading) {
        CenteredCircularProgress()
    }
}

@Preview
@Composable
fun PostScreenPreview() {
    val navController = rememberNavController()
    val moods = rememberMoodItems()
    var formState by remember {
        mutableStateOf(PostFormState())
    }
    val isFormValid by remember {
        mutableStateOf(
            formState.isImageListValid
                    && formState.isCurrentMoodValid
                    && formState.isPostTextValid
                    && formState.isSympathyMoodItemsValid
        )

    }
    PostScreen(
        navController = navController,
        isValid = isFormValid,
        formState = formState,
        moods = moods,
        onAddImage = { /*TODO*/ },
        onEvent = {
            when (it) {
                is PostFormEvent.CurrentMoodChanged -> {
                    formState = formState.copy(
                        currentMood = it.mood
                    )
                }
                is PostFormEvent.ImageListUpdated -> {
                    formState = formState.copy(
                        images = it.images ?: emptyList()
                    )
                }
                is PostFormEvent.PostTextChanged -> {
                    formState = formState.copy(
                        postText = it.text
                    )
                }
                is PostFormEvent.OpenChanged -> {
                    formState = formState.copy(
                        isOpened = it.open
                    )
                }
                is PostFormEvent.AnonymousChanged -> {
                    formState = formState.copy(
                        isAnonymous = it.anonymous
                    )
                }
                is PostFormEvent.SympathyItemsChanged -> {
                    formState = formState.copy(
                        sympathyMoodItems = it.sympathyItems
                    )
                }
                is PostFormEvent.DialogVisibilityChanged -> {
                    formState = formState.copy(
                        isDialogVisible = it.isVisible
                    )
                }
            }
        },
        onSubmit = {},
        uiState = UiState<Unit?>()
    )
}