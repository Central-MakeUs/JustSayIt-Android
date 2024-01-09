package com.sowhat.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf

@Composable
fun JustSayItTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val typography = JustSayItTypography()
    val colorScheme = remember {
        if (darkTheme) DarkColorScheme else LightColorScheme
    }

    CompositionLocalProvider(
        LocalColors provides colorScheme,
        LocalTypography provides typography,
    ) {
        content()
    }
}

object JustSayItTheme {
    val Colors: JustSayItColor
        @Composable
        get() = LocalColors.current
    val Typography : JustSayItTypography
        @Composable
        get() = LocalTypography.current
}

val LightColorScheme = JustSayItColor(
    mainTypo = Gray900,
    subTypo = Gray700,
    mainBackground = Gray100,
    subBackground = Gray200,
    angry = AngryColor,
    happy = HappyColor,
    surprise = SurpriseColor,
    sad = SadColor
)

val DarkColorScheme = JustSayItColor(
    mainTypo = Gray900,
    subTypo = Gray700,
    mainBackground = Gray100,
    subBackground = Gray200,
    angry = AngryColor,
    happy = HappyColor,
    surprise = SurpriseColor,
    sad = SadColor
)

val LocalTypography = staticCompositionLocalOf { JustSayItTypography() }
val LocalColors = staticCompositionLocalOf { LightColorScheme }

