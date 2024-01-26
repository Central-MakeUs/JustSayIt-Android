package com.sowhat.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf

@Composable
fun JustSayItTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val typography = JustSayItTypography()
    val shape = JustSayItShape()
    val colorScheme = remember {
        if (darkTheme) DarkColorScheme else LightColorScheme
    }
    val dimensions = JustSayItDimension()

    CompositionLocalProvider(
        LocalColors provides colorScheme,
        LocalTypography provides typography,
        LocalShapes provides shape,
        LocalSpacing provides dimensions
    ) {
        content()
    }
}

object JustSayItTheme {
    val Colors: JustSayItColor
        @Composable
        get() = LocalColors.current
    val Typography: JustSayItTypography
        @Composable
        get() = LocalTypography.current
    val Shapes: JustSayItShape
        @Composable
        get() = LocalShapes.current
    val Spacing: JustSayItDimension
        @Composable
        get() = LocalSpacing.current
}

val LightColorScheme = JustSayItColor(
    mainTypo = Gray900,
    subTypo = Gray700,
    mainBackground = White,
    subBackground = Gray200,
    mainSurface = White,
    onMainSurface = NavIconLight,
    subSurface = Gray400,
    angry = AngryColor,
    happy = HappyColor,
    surprise = SurpriseColor,
    sad = SadColor,
    inactiveTypo = Gray500
)

val DarkColorScheme = JustSayItColor(
    mainTypo = White,
    subTypo = Gray100,
    mainBackground = Black,
    subBackground = Gray800,
    mainSurface = Black,
    onMainSurface = White,
    subSurface = Gray800,
    angry = AngryColor,
    happy = HappyColor,
    surprise = SurpriseColor,
    sad = SadColor,
    inactiveTypo = Gray500
)

val LocalTypography = staticCompositionLocalOf { JustSayItTypography() }
val LocalColors = staticCompositionLocalOf { LightColorScheme }
val LocalShapes = staticCompositionLocalOf { JustSayItShape() }
val LocalSpacing = compositionLocalOf { JustSayItDimension() }
