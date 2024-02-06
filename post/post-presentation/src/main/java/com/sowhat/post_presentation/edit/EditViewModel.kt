package com.sowhat.post_presentation.edit

import android.net.Uri
import android.util.Log
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
import com.sowhat.database.entity.MyFeedEntity
import com.sowhat.designsystem.common.Mood
import com.sowhat.designsystem.common.MoodItem
import com.sowhat.post_domain.use_case.EditPostUseCase
import com.sowhat.post_domain.use_case.GetFeedDataUseCase
import com.sowhat.post_domain.use_case.SubmitPostUseCase
import com.sowhat.post_domain.use_case.ValidateCurrentMoodUseCase
import com.sowhat.post_domain.use_case.ValidatePostImagesUseCase
import com.sowhat.post_domain.use_case.ValidatePostTextUseCase
import com.sowhat.post_domain.use_case.ValidateSympathyUseCase
import com.sowhat.post_presentation.common.EditFormEvent
import com.sowhat.post_presentation.common.EditFormState
import com.sowhat.post_presentation.util.MultipartConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val getFeedDataUseCase: GetFeedDataUseCase,
    private val editPostUseCase: EditPostUseCase,
    private val validateCurrentMoodUseCase: ValidateCurrentMoodUseCase,
    private val validatePostImagesUseCase: ValidatePostImagesUseCase,
    private val validatePostTextUseCase: ValidatePostTextUseCase,
    private val validateSympathyUseCase: ValidateSympathyUseCase,
    private val multipartConverter: MultipartConverter,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _formState = MutableStateFlow(
        EditFormState(
            isCurrentMoodValid = true,
            isPostTextValid = true,
            isImageListValid = true,
            isSympathyMoodItemsValid = true,
        )
    )
    val formState = _formState.asStateFlow()

    private var isChanged = MutableStateFlow(false)

    var uiState by mutableStateOf(UiState<Unit?>())
        private set

    private val postingEventChannel = Channel<PostingEvent>()
    val postingEvent = postingEventChannel.receiveAsFlow()

    val isFormValid by derivedStateOf {
        formState.value.isImageListValid
            && formState.value.isCurrentMoodValid
            && formState.value.isPostTextValid
            && formState.value.isSympathyMoodItemsValid
            && isChanged.value
    }

    private lateinit var initialFormStatus: EditFormState

    fun getFeedData(
        feedId: Long,
        moodItems: List<MoodItem>,
        from: String
    ) {
        viewModelScope.launch {
            getFeedDataUseCase(feedId, from)?.let { feedEntity ->
                updateInitialFormState(moodItems, feedEntity)
            } ?: Log.e(TAG, "feed data is null")
        }
    }

    private fun updateInitialFormState(
        moodItems: List<MoodItem>,
        feedEntity: MyFeedEntity
    ) {
        _formState.update { currentForm ->
            editFormState(currentForm, moodItems, feedEntity)
        }

        initialFormStatus = formState.value
        Log.i(TAG, "updateInitialFormState: form state : ${formState.value}")
    }

    private fun editFormState(
        currentForm: EditFormState,
        moodItems: List<MoodItem>,
        feedEntity: MyFeedEntity
    ) = currentForm.copy(
        storyId = feedEntity.storyId,
        currentMood = moodItems.find { it.postData == feedEntity.writerEmotion },
        postText = feedEntity.bodyText,
        images = feedEntity.photo.map { Uri.parse(it) },
        existingUrl = feedEntity.photo,
        existingUrlId = feedEntity.photoId ?: emptyList(),
        isOpened = feedEntity.isOpened,
        isAnonymous = feedEntity.isAnonymous,
        sympathyMoodItems = getCurrentSympathyItems(
            isHappySelected = feedEntity.isHappinessSelected,
            isSadSelected = feedEntity.isSadnessSelected,
            isSurprisedSelected = feedEntity.isSurprisedSelected,
            isAngrySelected = feedEntity.isAngrySelected,
            moodItems = moodItems
        )
    )

    private fun getCurrentSympathyItems(
        isHappySelected: Boolean,
        isSadSelected: Boolean,
        isSurprisedSelected: Boolean,
        isAngrySelected: Boolean,
        moodItems: List<MoodItem>
    ): List<MoodItem> {
        val sympathyItems = mutableListOf<MoodItem>()

        if (isHappySelected) {
            moodItems.find { it.postData == Mood.HAPPY.postData }?.let {
                sympathyItems.add(it)
            }
        }

        if (isSadSelected) {
            moodItems.find { it.postData == Mood.SAD.postData }?.let {
                sympathyItems.add(it)
            }
        }

        if (isSurprisedSelected) {
            moodItems.find { it.postData == Mood.SURPRISED.postData }?.let {
                sympathyItems.add(it)
            }
        }

        if (isAngrySelected) {
            moodItems.find { it.postData == Mood.ANGRY.postData }?.let {
                sympathyItems.add(it)
            }
        }

        return sympathyItems
    }

    fun submitPost() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            if (isFormValid) {
                val postImages = formState.value.images.filter {
                    it.toString() !in formState.value.existingUrl
                }
                val multipartList = multipartConverter.convertUriIntoMultipart(postImages)
                val requestBody = multipartConverter.getEditRequestBodyData(formState.value)

                requestSubmit(requestBody, multipartList)
            }
        }
    }

    private suspend fun requestSubmit(
        requestBody: RequestBody?,
        multipartList: List<MultipartBody.Part>?
    ) {
        requestBody?.let {
            val result = editPostUseCase(requestBody, multipartList)

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

    fun onEvent(event: EditFormEvent) {
        when (event) {
            is EditFormEvent.CurrentMoodChanged -> {
                val previousMood = formState.value.currentMood
                val selectedMood = event.mood
                val currentSympathyItems = formState.value.sympathyMoodItems.toMutableList()

                val isOpen = formState.value.isOpened
                if (isOpen && selectedMood !in currentSympathyItems) currentSympathyItems.add(selectedMood)

                previousMood?.let {
                    val isPreviousItemRemovalValid = it in currentSympathyItems && it != selectedMood
                    if (isPreviousItemRemovalValid) currentSympathyItems.remove(it)
                }

                _formState.value = formState.value.copy(
                    currentMood = selectedMood,
                    isCurrentMoodValid = validateCurrentMoodUseCase(selectedMood).isValid,
                    sympathyMoodItems = currentSympathyItems,
                    isSympathyMoodItemsValid = validateSympathyUseCase(isOpen, currentSympathyItems).isValid
                )

                isChanged()
            }
            is EditFormEvent.ImageListUpdated -> {
                val uris = event.images
                val isValid = validatePostImagesUseCase(uris).isValid
                _formState.value = formState.value.copy(
                    images = uris ?: emptyList(),
                    isImageListValid = isValid
                )

                isChanged()
            }
            is EditFormEvent.PostTextChanged -> {
                val postText = event.text
                val isValid = validatePostTextUseCase(postText).isValid
                _formState.value = formState.value.copy(
                    postText = postText,
                    isPostTextValid = isValid
                )

                isChanged()
            }
            is EditFormEvent.OpenChanged -> {
                _formState.value = formState.value.copy(
                    isOpened = event.open
                )
                if (!formState.value.isOpened) {
                    _formState.value = formState.value.copy(
                        isAnonymous = false,
                        sympathyMoodItems = emptyList()
                    )
                } else {
                    val currentMood = formState.value.currentMood
                    currentMood?.let {
                        _formState.value = formState.value.copy(
                            sympathyMoodItems = listOf(currentMood)
                        )
                    }
                }

                isChanged()
            }
            is EditFormEvent.AnonymousChanged -> {
                _formState.value = formState.value.copy(
                    isAnonymous = event.anonymous
                )

                isChanged()
            }
            is EditFormEvent.SympathyItemsChanged -> {
                val sympathyItems = event.sympathyItems
                val isValid = validateSympathyUseCase(formState.value.isOpened, sympathyItems).isValid
                val currentMood = formState.value.currentMood
                currentMood?.let {
                    if (currentMood !in sympathyItems) {
                        return
                    }
                }
                _formState.value = formState.value.copy(
                    sympathyMoodItems = sympathyItems,
                    isSympathyMoodItemsValid = isValid
                )

                isChanged()
            }
            is EditFormEvent.DialogVisibilityChanged -> {
                _formState.value = formState.value.copy(
                    isDialogVisible = event.isVisible
                )
            }
            is EditFormEvent.DeletedUrlIdAdded -> {
                _formState.value = formState.value.copy(
                    deletedUrlId = (formState.value.deletedUrlId + event.urlIds).toSet().toList()
                )
                Log.i(TAG, "onEvent: image deleted: ${formState.value.deletedUrlId}")

                isChanged()
            }
        }
    }

    private fun isChanged() {
        isChanged.value = initialFormStatus != formState.value
    }

    companion object {
        private const val TAG = "EditViewModel"
        private const val EDIT_STATE = "edit_state"
    }
}