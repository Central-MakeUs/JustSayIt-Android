package com.sowhat.post_presentation.posting

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.sowhat.common.model.PostFormEvent
import com.sowhat.common.model.PostFormState
import com.sowhat.common.model.PostingEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(

) : ViewModel() {
    var formState by mutableStateOf(PostFormState())
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

    }

    fun onEvent(event: PostFormEvent) {
        when (event) {
            is PostFormEvent.CurrentMoodChanged -> {
                formState = formState.copy(
                    currentMood = event.mood
                )
            }
            is PostFormEvent.ImageListUpdated -> {
                formState = formState.copy(
                    images = event.images ?: emptyList()
                )
            }
            is PostFormEvent.PostTextChanged -> {
                formState = formState.copy(
                    postText = event.text
                )
            }
            is PostFormEvent.OpenChanged -> {
                formState = formState.copy(
                    isOpened = event.open
                )
            }
            is PostFormEvent.AnonymousChanged -> {
                formState = formState.copy(
                    isAnonymous = event.anonymous
                )
            }
            is PostFormEvent.SympathyItemsChanged -> {
                formState = formState.copy(
                    sympathyMoodItems = event.sympathyItems
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