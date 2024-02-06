package com.sowhat.post_presentation.edit

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sowhat.common.model.PostingEvent
import com.sowhat.common.model.UiState
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
import com.sowhat.post_presentation.common.EditFormEvent
import com.sowhat.post_presentation.common.EditFormState
import com.sowhat.post_presentation.common.PostFormEvent
import com.sowhat.post_presentation.common.PostFormState
import com.sowhat.post_presentation.common.SubjectItem
import com.sowhat.post_presentation.component.CurrentMoodSelection
import com.sowhat.post_presentation.component.ImageSelection
import com.sowhat.post_presentation.component.PostText
import com.sowhat.post_presentation.component.PostToggle
import com.sowhat.post_presentation.component.SympathySelection
import com.sowhat.post_presentation.navigation.navigateBack

@Composable
fun EditRoute(
    navController: NavController,
    feedId: Long,
    from: String,
    viewModel: EditViewModel = hiltViewModel()
) {
    val formState = viewModel.formState.collectAsState().value
    val moodListItems = rememberMoodItems()
    val uiState = viewModel.uiState
    val moods = rememberMoodItems()
    val context = LocalContext.current
    val availableImageCount = 4

    LaunchedEffect(key1 = true) {
        viewModel.getFeedData(feedId, moodListItems, from)
    }

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

                viewModel.onEvent(EditFormEvent.ImageListUpdated(currentImages))
            }
        }
    )

    EditScreen(
        navController = navController,
        isValid = viewModel.isFormValid,
        formState = formState,
        uiState = uiState,
        moods = moods,
        onAddImage = { imagePicker.launch("image/*") },
        onEvent = viewModel::onEvent,
        onSubmit = viewModel::submitPost
    )
}

@Composable
fun EditScreen(
    navController: NavController,
    isValid: Boolean,
    formState: EditFormState,
    uiState: UiState<Unit?>,
    moods: List<MoodItem>,
    onAddImage: () -> Unit,
    onEvent: (EditFormEvent) -> Unit,
    onSubmit: () -> Unit
) {
    val textFocusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    BackHandler(
        enabled = true
    ) {
        if (!formState.isDialogVisible) {
            onEvent(EditFormEvent.DialogVisibilityChanged(true))
        } else {
            onEvent(EditFormEvent.DialogVisibilityChanged(false))
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
                    onEvent(EditFormEvent.DialogVisibilityChanged(true))
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
                    text = stringResource(R.string.button_edit),
                    isActive = isValid,
                    onClick = { onSubmit() }
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
                        onEvent(EditFormEvent.CurrentMoodChanged(changedMood))
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
                        onEvent(EditFormEvent.PostTextChanged(changedText))
                    },
                    maxLength = 300
                )
            }

            item {
                ImageSelection(
                    images = formState.images,
                    onAddClick = { onAddImage() },
                    onImagesChange = { images ->
                        val deletedImageIds = mutableListOf<Long>()
                        formState.existingUrl.zip(formState.existingUrlId) { url, id ->
                            if (url !in images.map { uri -> uri.toString() }) {
                                deletedImageIds.add(id)
                            }
                        }
                        onEvent(EditFormEvent.DeletedUrlIdAdded(deletedImageIds))
                        onEvent(EditFormEvent.ImageListUpdated(images))
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
                        onEvent(EditFormEvent.OpenChanged(updatedState))
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
                        onEvent(EditFormEvent.AnonymousChanged(updatedState))
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
                        onEvent(EditFormEvent.SympathyItemsChanged(currentItems))
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
                title = stringResource(id = R.string.dialog_title_editing),
                subTitle = stringResource(
                    id = R.string.dialog_subtitle_editing
                ),
                buttonContent = stringResource(id = R.string.dialog_button_continue_edit) to stringResource(
                    id = R.string.dialog_button_stop_edit
                ),
                onAccept = {
                    onEvent(EditFormEvent.DialogVisibilityChanged(false))
                    navController.navigateBack()
                },
                onDismiss = { onEvent(EditFormEvent.DialogVisibilityChanged(false)) }
            )
        }
    }

    if (uiState.isLoading) {
        CenteredCircularProgress()
    }
}