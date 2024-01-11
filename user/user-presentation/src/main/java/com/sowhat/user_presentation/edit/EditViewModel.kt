package com.sowhat.user_presentation.edit

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.sowhat.designsystem.R
import com.sowhat.designsystem.component.ProfileImage
import com.sowhat.designsystem.theme.Gray200
import com.sowhat.designsystem.theme.Gray400
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(

) : ViewModel() {
    var profileUrl by mutableStateOf("https://github.com/kmkim2689/Android-Wiki/assets/101035437/88d7b249-ad72-4be9-8d79-38dc942e0a7f")
    var userName by mutableStateOf("케이엠")
    var newUserName by mutableStateOf("")
    var hasImage by mutableStateOf(false)
    var newImageUri by mutableStateOf<Uri?>(null)
    val isValid by derivedStateOf {
        userName.length in (2..12) || (newImageUri != null && hasImage)
    }
    var dropdown by mutableStateOf(false)
}