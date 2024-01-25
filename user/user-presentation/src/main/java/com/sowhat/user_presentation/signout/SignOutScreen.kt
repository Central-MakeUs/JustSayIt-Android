package com.sowhat.user_presentation.signout

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sowhat.common.model.PostingEvent
import com.sowhat.common.model.SignOutEvent
import com.sowhat.common.util.ObserveEvents
import com.sowhat.designsystem.component.AppBar
import com.sowhat.designsystem.R
import com.sowhat.designsystem.component.AlertDialog
import com.sowhat.designsystem.component.AlertDialogReverse
import com.sowhat.designsystem.theme.JustSayItTheme
import com.sowhat.user_presentation.common.MenuItem
import com.sowhat.user_presentation.common.SignOutUiState
import com.sowhat.user_presentation.component.Menu
import com.sowhat.user_presentation.navigation.navigateToOnboarding
import com.sowhat.user_presentation.navigation.navigateUpToSetting
import kotlinx.coroutines.flow.update

@Composable
fun SignOutRoute(
    appNavController: NavController,
    viewModel: SignOutViewModel = hiltViewModel()
) {

    val uiState = viewModel.uiState.collectAsState().value

    SignOutScreen(
        appNavController = appNavController,
        onEvent = viewModel::onEvent
    )

    ScreenDialog(
        appNavController = appNavController,
        uiState = uiState,
        onSignOut = viewModel::signOut,
        onWithdraw = viewModel::withdraw,
        onEvent = viewModel::onEvent
    )

    ObserveEvents(flow = viewModel.signOutEvent) { uiEvent ->
        when (uiEvent) {
            is PostingEvent.Error -> {

            }
            is PostingEvent.NavigateUp -> {
                appNavController.navigateToOnboarding()
                Log.i("SignOutScreen", appNavController.visibleEntries.value.toString())
            }
        }
    }

}

@Composable
private fun ScreenDialog(
    appNavController: NavController,
    uiState: SignOutUiState,
    onSignOut: () -> Unit,
    onWithdraw: () -> Unit,
    onEvent: (SignOutEvent) -> Unit
) {
    if (uiState.showSignOut) {
        AlertDialogReverse(
            title = stringResource(id = R.string.dialog_title_sign_out),
            subTitle = stringResource(id = R.string.dialog_subtitle_sign_out),
            buttonContent = stringResource(id = R.string.dialog_button_cancel)
                    to stringResource(id = R.string.dialog_button_sign_out),
            onAccept = onSignOut,
            onDismiss = {
                onEvent(SignOutEvent.SignOutVisibilityChanged(false))
            }
        )
    }

    if (uiState.showWithdraw) {
        AlertDialogReverse(
            title = stringResource(id = R.string.dialog_title_withdraw),
            subTitle = stringResource(id = R.string.dialog_subtitle_withdraw),
            buttonContent = stringResource(id = R.string.dialog_button_cancel)
                    to stringResource(id = R.string.dialog_button_withdraw),
            onAccept = onWithdraw,
            onDismiss = {
                onEvent(SignOutEvent.WithdrawVisibilityChanged(false))
                appNavController.popBackStack()
            }
        )
    }
}

@Composable
fun SignOutScreen(
    appNavController: NavController,
    onEvent: (SignOutEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        contentColor = contentColorFor(
            backgroundColor = JustSayItTheme.Colors.mainBackground
        ),
        topBar = {
            AppBar(
                title = stringResource(id = R.string.setting_item_indie_info),
                navigationIcon = R.drawable.ic_back_24,
                actionIcon = null,
                onNavigationIconClick = { appNavController.navigateUpToSetting() }
            )
        }
    ) { paddingValues ->
        SignOutScreenContent(
            paddingValues = paddingValues,
            onEvent = onEvent
        )
    }
}

@Composable
private fun SignOutScreenContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    onEvent: (SignOutEvent) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(JustSayItTheme.Colors.mainBackground)
    ) {
        Menu(
            modifier = Modifier
                .padding(vertical = JustSayItTheme.Spacing.spaceSm)
                .fillMaxWidth(),
            title = null,
            menus = listOf(
                MenuItem(
                    title = stringResource(id = R.string.setting_item_sign_out),
                    onClick = { onEvent(SignOutEvent.SignOutVisibilityChanged(true)) }
                ),
                MenuItem(
                    title = stringResource(id = R.string.setting_item_withdraw),
                    onClick = { onEvent(SignOutEvent.WithdrawVisibilityChanged(true)) }
                )
            )
        )
    }
}

@Preview
@Composable
fun SignOutScreenPreview() {
    val onEvent = { event: SignOutEvent ->
        when (event) {
            is SignOutEvent.SignOutVisibilityChanged -> {
                // update : for mutablestateflow -> concurrently update
//                _uiState.update {
//                    uiState.value.copy(showSignOut = event.isVisible)
//                }
            }
            is SignOutEvent.WithdrawVisibilityChanged -> {
//                _uiState.update {
//                    uiState.value.copy(showWithdraw = event.isVisible)
//                }
            }
        }
    }

    SignOutScreen(
        appNavController = rememberNavController(),
        onEvent = onEvent
    )
}