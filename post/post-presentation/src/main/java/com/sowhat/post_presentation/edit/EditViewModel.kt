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
import com.sowhat.common.model.UiState
import com.sowhat.database.entity.MyFeedEntity
import com.sowhat.designsystem.common.Emotion
import com.sowhat.designsystem.common.Mood
import com.sowhat.designsystem.common.MoodItem
import com.sowhat.post_domain.use_case.GetFeedDataUseCase
import com.sowhat.post_presentation.common.EditFormState
import com.sowhat.post_presentation.common.PostFormState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val getFeedDataUseCase: GetFeedDataUseCase,
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

    var uiState by mutableStateOf(UiState<Unit?>())
        private set

    val isFormValid by derivedStateOf {
        formState.value.isImageListValid
            && formState.value.isCurrentMoodValid
            && formState.value.isPostTextValid
            && formState.value.isSympathyMoodItemsValid
            && formState.value.isChanged
    }

    fun getFeedData(feedId: Long, moodItems: List<MoodItem>) {
        viewModelScope.launch {
            getFeedDataUseCase(feedId)?.let { feedEntity ->
                updateInitialFormState(moodItems, feedEntity)
            } ?: Log.e(TAG, "feed data is null")
        }
    }

    private fun updateInitialFormState(
        moodItems: List<MoodItem>,
        feedEntity: MyFeedEntity
    ) {
        _formState.update { currentForm ->
            currentForm.copy(
                currentMood = moodItems.find { it.postData == feedEntity.writerEmotion },
                postText = feedEntity.bodyText,
                images = feedEntity.photo.map {
                    Uri.parse(it)
                },
                existingUrl = feedEntity.photo,
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
        }

        Log.i(TAG, "updateInitialFormState: form state : ${formState.value}")
    }

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


    companion object {
        private const val TAG = "EditViewModel"
        private const val EDIT_STATE = "edit_state"
    }
}