package com.sowhat.authentication_presentation.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.sowhat.authentication_presentation.navigation.navigateToMain
import com.sowhat.authentication_presentation.navigation.navigateToOnboarding
import com.sowhat.common.model.SplashEvent
import com.sowhat.common.util.ObserveEvents
import com.sowhat.designsystem.R
import com.sowhat.designsystem.theme.JustSayItTheme

@Composable
fun SplashRoute(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel()
) {
    ObserveEvents(flow = viewModel.uiEvent) { event ->
        when (event) {
            is SplashEvent.NavigateToMain -> {
                navController.navigateToMain(
                    popUpTo = navController.currentDestination?.route ?: return@ObserveEvents
                )
            }
            is SplashEvent.NavigateToSignIn -> {
                navController.navigateToOnboarding(
                    popUpTo = navController.currentDestination?.route ?: return@ObserveEvents
                )
            }
            is SplashEvent.Error -> {

            }
        }
    }

    SplashScreen(navController = navController)
}

@Composable
fun SplashScreen(
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = JustSayItTheme.Colors.mainBackground)
    ) {
        AnimatedPreloader(modifier = Modifier.fillMaxSize())
    }
}

@Composable
fun AnimatedPreloader(modifier: Modifier = Modifier) {
    val preloaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            R.raw.anim_splash
        )
    )

    val preloaderProgress by animateLottieCompositionAsState(
        preloaderLottieComposition,
        iterations = 1,
        isPlaying = true
    )


    LottieAnimation(
        modifier = modifier,
        composition = preloaderLottieComposition,
        progress = { preloaderProgress },
        contentScale = ContentScale.Crop
    )
}