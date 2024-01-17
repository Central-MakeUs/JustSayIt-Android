package com.sowhat.post_presentation.posting

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sowhat.common.model.PostFormEvent
import com.sowhat.common.model.PostFormState
import com.sowhat.common.model.PostingEvent
import com.sowhat.common.model.RegistrationFormEvent
import com.sowhat.common.model.SignUpEvent
import com.sowhat.common.util.ObserveEvents
import com.sowhat.common.util.getFile
import com.sowhat.designsystem.common.MoodItem
import com.sowhat.designsystem.component.AppBar
import com.sowhat.designsystem.component.DefaultButtonFull
import com.sowhat.post_presentation.R
import com.sowhat.post_presentation.common.SubjectItem
import com.sowhat.post_presentation.common.rememberMoodItems
import com.sowhat.post_presentation.component.CurrentMoodSelection
import com.sowhat.post_presentation.component.ImageSelection
import com.sowhat.post_presentation.component.PostText
import com.sowhat.post_presentation.component.PostToggle
import com.sowhat.post_presentation.component.SympathySelection
import com.sowhat.post_presentation.navigation.navigateBack
import javax.security.auth.Subject

@Composable
fun PostRoute(
    navController: NavController,
    viewModel: PostViewModel = hiltViewModel()
) {
    val formState = viewModel.formState
    val moods = rememberMoodItems()
    val context = LocalContext.current

    // TODO MoodItem 데이터 클래스 postData(서버로 실제로 보낼 감정 문자열 데이터) api 확정 시 수정해놓기

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                // TODO val file = getFile(context, it, "/* 서버에서 나오는 것대로 수정 필요 */")
                val currentImages = formState.images.toMutableList()
                currentImages.add(it)
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
        onEvent = viewModel::onEvent,
        onSubmit = viewModel::submitPost,
        onAddImage = {
            imagePicker.launch("image/*")
        },
        moods = moods
    )
}

@Composable
fun PostScreen(
    navController: NavController,
    formState: PostFormState,
    moods: List<MoodItem>,
    onAddImage: () -> Unit,
    onEvent: (PostFormEvent) -> Unit,
    onSubmit: () -> Unit
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
                onClick = { onSubmit() }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
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

            PostText(
                subject = SubjectItem(
                    title = stringResource(id = com.sowhat.designsystem.R.string.title_write),
                    subTitle = stringResource(id = com.sowhat.designsystem.R.string.subtitle_write)
                ),
                text = formState.postText,
                onTextChange = { changedText ->
                    onEvent(PostFormEvent.PostTextChanged(changedText))
                },
                maxLength = 300
            )

            ImageSelection(
                images = formState.images,
                onAddClick = { onAddImage() },
                onImagesChange = { images ->
                    onEvent(PostFormEvent.ImageListUpdated(images))
                }
            )

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

            SympathySelection(
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
    }
}