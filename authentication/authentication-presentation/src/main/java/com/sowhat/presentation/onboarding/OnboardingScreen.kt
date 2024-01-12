package com.sowhat.presentation.onboarding

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sowhat.designsystem.R
import com.sowhat.designsystem.common.APP_INTRO
import com.sowhat.designsystem.common.APP_NAME
import com.sowhat.designsystem.common.SIGN_IN
import com.sowhat.designsystem.theme.JustSayItTheme
import com.sowhat.presentation.common.SignInPlatform
import com.sowhat.presentation.component.Logo
import com.sowhat.presentation.component.SignIn
import com.sowhat.presentation.component.TermText
import com.sowhat.presentation.navigation.CONFIGURATION
import com.sowhat.presentation.navigation.navigateToUserConfig
import com.sowhat.util.NaverOAuthClient
import kotlinx.coroutines.launch

@Composable
fun OnboardingRoute(
    navController: NavHostController
) {
    OnboardingScreen(navController = navController)
}

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val signInPlatforms = listOf(
        SignInPlatform(
            iconDrawable = R.drawable.ic_naver_54,
            onClick = {
                scope.launch {
                    val accessToken = NaverOAuthClient.signIn(context)
                    if (accessToken != null) navController.navigateToUserConfig()
                }
            }
        )
    )

    ConstraintLayout(
        modifier = modifier.fillMaxSize()
    ) {
        val (logo, signIn, terms) = createRefs()
        val topSpace = JustSayItTheme.Spacing.spaceExtraExtraLarge
        val signInTermSpace = JustSayItTheme.Spacing.spaceExtraLarge

        Logo(
            modifier = Modifier
                .constrainAs(logo) {
                    top.linkTo(parent.top, margin = 160.dp)
                    bottom.linkTo(signIn.top)
                },
            title = APP_NAME,
            subTitle = APP_INTRO
        )

        SignIn(
            modifier = Modifier
                .constrainAs(signIn) {
                    top.linkTo(logo.bottom)
                    bottom.linkTo(parent.bottom, margin = 60.dp)
                },
            headerText = SIGN_IN,
            signInPlatforms = signInPlatforms,
        )

        TermText(
            modifier = Modifier
                .constrainAs(terms) {
                    top.linkTo(signIn.bottom, margin = signInTermSpace)
                }
                .fillMaxWidth()
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun OnboardingScreenPreview() {
    val navController = rememberNavController()
    OnboardingScreen(navController = navController)
}