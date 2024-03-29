package com.sowhat.authentication_presentation.onboarding

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.identity.Identity
import com.sowhat.common.util.ObserveEvents
import com.sowhat.common.model.SignInEvent
import com.sowhat.designsystem.R
import com.sowhat.designsystem.common.APP_INTRO
import com.sowhat.designsystem.common.APP_NAME
import com.sowhat.designsystem.common.SIGN_IN
import com.sowhat.designsystem.component.CenteredCircularProgress
import com.sowhat.designsystem.theme.JustSayItTheme
import com.sowhat.authentication_presentation.common.Platform
import com.sowhat.authentication_presentation.common.SignInPlatform
import com.sowhat.authentication_presentation.component.AppTitle
import com.sowhat.authentication_presentation.component.SignIn
import com.sowhat.authentication_presentation.component.TermText
import com.sowhat.authentication_presentation.navigation.navigateToMain
import com.sowhat.authentication_presentation.navigation.navigateToUserConfig
import com.sowhat.authentication_presentation.util.GoogleOAuthClient
import com.sowhat.common.util.NaverOAuthClient
import com.sowhat.common.navigation.ONBOARDING
import kotlinx.coroutines.launch

@Composable
fun OnboardingRoute(
    navController: NavHostController,
    viewModel: OnboardingViewModel = hiltViewModel()
) {

    val isLoading = viewModel.uiState.collectAsState().value.isLoading

    ObserveEvents(flow = viewModel.uiEvent) { uiEvent ->
        when (uiEvent) {
            is SignInEvent.NavigateToMain -> {
                Log.i("OnboardingScreen", "navigate to main")
                navController.navigateToMain(popUpTo = ONBOARDING)
            }
            is SignInEvent.NavigateToSignUp -> {
                Log.i("OnboardingScreen", "navigate to user config")
                navController.navigateToUserConfig()
            }
            is SignInEvent.Error -> {
                Log.i("OnboardingScreen", "error")
            }
        }
    }

    OnboardingScreen(
        onLoginStart = viewModel::signIn,
        isLoading = isLoading
    )
}

@Composable
private fun OnboardingScreen(
    modifier: Modifier = Modifier,
    onLoginStart: (Platform, String) -> Unit,
    isLoading: Boolean
) {
    val TAG = "OnboardingScreen"

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val googleOAuthClient by lazy {
        GoogleOAuthClient(context, Identity.getSignInClient(context.applicationContext))
    }

    suspend fun validateGoogleSignIn(result: ActivityResult) {
        val accessToken = googleOAuthClient.signInWithIntent(
            intent = result.data ?: return
        )
        Log.d(TAG, "Sign In Result : $accessToken")

        accessToken?.let {
            onLoginStart(Platform.GOOGLE, it)
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                scope.launch {
                    validateGoogleSignIn(result)
                }
            }
        }
    )

    val signInPlatforms = listOf(
//        SignInPlatform(
//            iconDrawable = R.drawable.ic_kakao_54,
//            onClick = {
//                scope.launch {
//                    val accessToken = KakaoOAuthClient.signIn(context)
//                    Log.i(TAG, "kakao access token : $accessToken")
//                    if (accessToken != null) onLoginStart(Platform.KAKAO, accessToken)
//                }
//            }
//        ),
        SignInPlatform(
            iconDrawable = R.drawable.ic_naver_54,
            onClick = {
                scope.launch {
                    val accessToken = NaverOAuthClient.signIn(context)
                    if (accessToken != null) onLoginStart(Platform.NAVER, accessToken)
                }
            }
        ),
//        SignInPlatform(
//            iconDrawable = R.drawable.ic_google_54,
//            onClick = {
//                scope.launch {
//                    val signInIntentSender = googleOAuthClient.signIn()
//                    Log.d(TAG, "Google Sign In entering")
//                    launcher.launch(
//                        IntentSenderRequest.Builder(
//                            signInIntentSender ?: return@launch
//                        ).build()
//                    )
//                }
//            }
//        )
    )

    OnboardingScreenContent(
        modifier = modifier,
        signInPlatforms = signInPlatforms,
        isLoading = isLoading
    )
}

@Composable
private fun OnboardingScreenContent(
    modifier: Modifier,
    signInPlatforms: List<SignInPlatform>,
    isLoading: Boolean
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(JustSayItTheme.Colors.mainBackground)
    ) {
        val (logo, appTitle, signIn, terms, loading) = createRefs()
        val topSpace = JustSayItTheme.Spacing.spaceExtraExtraLarge
        val signInTermSpace = JustSayItTheme.Spacing.spaceXXL

        Icon(
            modifier = Modifier.constrainAs(logo) {
                top.linkTo(parent.top, margin = 180.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            painter = painterResource(id = R.drawable.ic_logo_black),
            contentDescription = "logo",
            tint = JustSayItTheme.Colors.mainTypo
        )

        AppTitle(
            modifier = Modifier
                .constrainAs(appTitle) {
                    top.linkTo(logo.bottom, margin = 60.dp)
                },
            title = APP_NAME,
            subTitle = APP_INTRO
        )

        SignIn(
            modifier = Modifier
                .constrainAs(signIn) {
                    top.linkTo(appTitle.bottom, margin = 100.dp)
                },
            headerText = SIGN_IN,
            signInPlatforms = signInPlatforms,
        )

        TermText(
            modifier = Modifier
                .constrainAs(terms) {
                    top.linkTo(signIn.bottom, margin = 32.dp)
                }
                .fillMaxWidth()
        )

        if (isLoading) CenteredCircularProgress(
            modifier = Modifier.constrainAs(loading) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom, margin = 60.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun OnboardingScreenPreview() {
    OnboardingScreen(isLoading = false, onLoginStart = { _, _ ->})
}