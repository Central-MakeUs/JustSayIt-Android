package com.sowhat.post_presentation.edit

import android.util.Log
import androidx.activity.compose.BackHandler
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
import com.sowhat.common.model.UiState
import com.sowhat.designsystem.R
import com.sowhat.designsystem.common.MoodItem
import com.sowhat.designsystem.common.addFocusCleaner
import com.sowhat.designsystem.common.rememberMoodItems
import com.sowhat.designsystem.component.AlertDialog
import com.sowhat.designsystem.component.AppBar
import com.sowhat.designsystem.component.CenteredCircularProgress
import com.sowhat.designsystem.component.DefaultButtonFull
import com.sowhat.designsystem.theme.JustSayItTheme
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
    viewModel: EditViewModel = hiltViewModel()
) {
    val formState = viewModel.formState.collectAsState().value
    val moodListItems = rememberMoodItems()
    val uiState = viewModel.uiState
    val moods = rememberMoodItems()
    val context = LocalContext.current
    val availableImageCount = 4

    LaunchedEffect(key1 = true) {
        viewModel.getFeedData(feedId, moodListItems)
    }

//    EditScreen(
//        navController = navController,
//        formState = formState,
//        isValid = viewModel.isFormValid,
//        uiState = uiState,
//        moods = moods,
//        onAddImage =
//    )
}