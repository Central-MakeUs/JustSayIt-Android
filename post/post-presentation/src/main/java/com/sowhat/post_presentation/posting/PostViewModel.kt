package com.sowhat.post_presentation.posting

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sowhat.common.model.PostingEvent
import com.sowhat.common.model.Resource
import com.sowhat.common.model.UiState
import com.sowhat.post_domain.use_case.SubmitPostUseCase
import com.sowhat.post_domain.use_case.ValidateCurrentMoodUseCase
import com.sowhat.post_domain.use_case.ValidatePostImagesUseCase
import com.sowhat.post_domain.use_case.ValidatePostTextUseCase
import com.sowhat.post_domain.use_case.ValidateSympathyUseCase
import com.sowhat.post_presentation.common.PostFormEvent
import com.sowhat.post_presentation.common.PostFormState
import com.sowhat.post_presentation.util.MultipartConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val submitPostUseCase: SubmitPostUseCase,
    private val validateCurrentMoodUseCase: ValidateCurrentMoodUseCase,
    private val validatePostImagesUseCase: ValidatePostImagesUseCase,
    private val validatePostTextUseCase: ValidatePostTextUseCase,
    private val validateSympathyUseCase: ValidateSympathyUseCase,
    private val multipartConverter: MultipartConverter
) : ViewModel() {
    var formState by mutableStateOf(PostFormState())
        private set

    var uiState by mutableStateOf(UiState<Unit?>())
        private set

    val isFormValid by derivedStateOf {
        formState.isImageListValid
            && formState.isCurrentMoodValid
            && formState.isPostTextValid
            && formState.isSympathyMoodItemsValid
    }

    private val postingEventChannel = Channel<PostingEvent>()
    val postingEvent = postingEventChannel.receiveAsFlow()

    fun submitPost() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            if (isFormValid) {
                val multipartList = multipartConverter.convertUriIntoMultipart(formState.images)
                val requestBody = multipartConverter.getRequestBodyData(formState)

                requestSubmit(requestBody, multipartList)
            }
        }
    }

    private suspend fun requestSubmit(
        requestBody: RequestBody?,
        multipartList: List<MultipartBody.Part>?
    ) {
        requestBody?.let {
            val result = submitPostUseCase(requestBody, multipartList)

            when (result) {
                is Resource.Success -> {
                    uiState = uiState.copy(isLoading = false)
                    postingEventChannel.send(PostingEvent.NavigateUp)
                }

                is Resource.Error -> {
                    uiState = uiState.copy(isLoading = false, errorMessage = result.message)
                    postingEventChannel.send(
                        PostingEvent.Error(
                            result.message
                                ?: "예상치 못한 오류가 발생했습니다."
                        )
                    )
                }
            }
        }
    }


    fun onEvent(event: PostFormEvent) {
        when (event) {
            is PostFormEvent.CurrentMoodChanged -> {
                val previousMood = formState.currentMood
                val selectedMood = event.mood
                val currentSympathyItems = formState.sympathyMoodItems.toMutableList()

                val isOpen = formState.isOpened
                if (isOpen && selectedMood !in currentSympathyItems) currentSympathyItems.add(selectedMood)

                previousMood?.let {
                    val isPreviousItemRemovalValid = it in currentSympathyItems && it != selectedMood
                    if (isPreviousItemRemovalValid) currentSympathyItems.remove(it)
                }

                formState = formState.copy(
                    currentMood = selectedMood,
                    isCurrentMoodValid = validateCurrentMoodUseCase(selectedMood).isValid,
                    sympathyMoodItems = currentSympathyItems,
                    isSympathyMoodItemsValid = validateSympathyUseCase(isOpen, currentSympathyItems).isValid
                )
            }
            is PostFormEvent.ImageListUpdated -> {
                val uris = event.images
                val isValid = validatePostImagesUseCase(uris).isValid
                formState = formState.copy(
                    images = uris ?: emptyList(),
                    isImageListValid = isValid
                )
            }
            is PostFormEvent.PostTextChanged -> {
                val postText = event.text
                val isValid = validatePostTextUseCase(postText).isValid
                formState = formState.copy(
                    postText = postText,
                    isPostTextValid = isValid
                )
            }
            is PostFormEvent.OpenChanged -> {
                formState = formState.copy(
                    isOpened = event.open
                )
                if (!formState.isOpened) {
                    formState = formState.copy(
                        isAnonymous = false,
                        sympathyMoodItems = emptyList()
                    )
                } else {
                    val currentMood = formState.currentMood
                    currentMood?.let {
                        formState = formState.copy(
                            sympathyMoodItems = listOf(currentMood)
                        )
                    }
                }
            }
            is PostFormEvent.AnonymousChanged -> {
                formState = formState.copy(
                    isAnonymous = event.anonymous
                )
            }
            is PostFormEvent.SympathyItemsChanged -> {
                val sympathyItems = event.sympathyItems
                val isValid = validateSympathyUseCase(formState.isOpened, sympathyItems).isValid
                val currentMood = formState.currentMood
                currentMood?.let {
                    if (currentMood !in sympathyItems) {
                        return
                    }
                }
                formState = formState.copy(
                    sympathyMoodItems = sympathyItems,
                    isSympathyMoodItemsValid = isValid
                )
            }
            is PostFormEvent.DialogVisibilityChanged -> {
                formState = formState.copy(
                    isDialogVisible = event.isVisible
                )
            }
        }
    }
}