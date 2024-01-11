package com.sowhat.presentation.onboarding

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.sowhat.designsystem.R
import com.sowhat.designsystem.common.APP_INTRO
import com.sowhat.designsystem.common.APP_NAME
import com.sowhat.designsystem.common.SIGN_IN
import com.sowhat.presentation.common.SignInPlatform
import com.sowhat.presentation.component.Logo
import com.sowhat.presentation.component.SignIn
import com.sowhat.util.NaverOAuthClient
import kotlinx.coroutines.launch

@Composable
fun OnboardingRoute() {
    OnboardingScreen()
}

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val signInPlatforms = listOf(
        SignInPlatform(
            iconDrawable = R.drawable.ic_naver_54,
            onClick = {
                scope.launch {
                    val accessToken = NaverOAuthClient.signIn(context)
                    Log.i("OnBoardingScreen", "$accessToken")
                }
            }
        )
    )

    ConstraintLayout(
        modifier = modifier.fillMaxSize()
    ) {
        val (logo, signIn) = createRefs()

        Logo(
            modifier = Modifier
                .constrainAs(logo) {
                    top.linkTo(parent.top, margin = 170.dp)
                    bottom.linkTo(signIn.top)
                },
            title = APP_NAME,
            subTitle = APP_INTRO
        )

        SignIn(
            modifier = Modifier
                .constrainAs(signIn) {
                    top.linkTo(logo.bottom)
                    bottom.linkTo(parent.bottom)
                },
            headerText = SIGN_IN,
            signInPlatforms = signInPlatforms
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun OnboardingScreenPreview() {
    OnboardingScreen()
}