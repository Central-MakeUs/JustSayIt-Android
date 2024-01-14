package com.sowhat.designsystem.theme

import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.sp
import com.sowhat.designsystem.R

data class JustSayItTypography(
    val headlineB: TextStyle = HeadlineB,
    val headlineR: TextStyle = HeadlineR,
    val heading2: TextStyle = Heading2,
    val heading3: TextStyle = Heading3,
    val heading4: TextStyle = Heading4,
    val body1: TextStyle = Body1,
    val body2: TextStyle = Body2,
    val body3: TextStyle = Body3,
    val body4: TextStyle = Body4,
    val detail1: TextStyle = Detail1,
    val detail2: TextStyle = Detail2
)

private val Pretendard = FontFamily(
    Font(R.font.pretendard_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.pretendard_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.pretendard_light, FontWeight.Light, FontStyle.Normal),
    Font(R.font.pretendard_thin, FontWeight.Thin, FontStyle.Normal)
)

private val HeadlineB = TextStyle(
    fontFamily = Pretendard,
    fontWeight = FontWeight.Bold,
    fontSize = 24.sp,
    lineHeight = 18.sp,
    // https://velog.io/@hoyaho/%EB%B2%88%EC%97%AD-Jetpack-Compose-%EC%97%90%EC%84%9C-%ED%8F%B0%ED%8A%B8-%ED%8C%A8%EB%94%A9%EC%9D%84-%EC%88%98%EC%A0%95%ED%95%98%EB%8A%94-%EB%B0%A9%EB%B2%95#implementation-in-compose
    platformStyle = PlatformTextStyle(
        includeFontPadding = false
    ),
    lineHeightStyle = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.None
    ),
    color = Gray900
)

private val HeadlineR = TextStyle(
    fontFamily = Pretendard,
    fontWeight = FontWeight.Normal,
    fontSize = 24.sp,
    platformStyle = PlatformTextStyle(
        includeFontPadding = false
    ),
//    lineHeight = TextUnit(value = 1.4f, type = TextUnitType.Em),
//    letterSpacing = TextUnit(value = 0.75f, type = TextUnitType.Em),
    color = Gray900
)

private val Heading2 = TextStyle(
    fontFamily = Pretendard,
    fontWeight = FontWeight.Normal,
    fontSize = 22.sp,
    platformStyle = PlatformTextStyle(
        includeFontPadding = false
    ),
//    lineHeight = TextUnit(value = 1.45f, type = TextUnitType.Em),
//    letterSpacing = TextUnit(value = 0.75f, type = TextUnitType.Em),
    color = Gray900
)

private val Heading3 = TextStyle(
    fontFamily = Pretendard,
    fontWeight = FontWeight.Normal,
    fontSize = 20.sp,
    platformStyle = PlatformTextStyle(
        includeFontPadding = false
    ),
//    lineHeight = TextUnit(value = 1.4f, type = TextUnitType.Em),
//    letterSpacing = TextUnit(value = 0.75f, type = TextUnitType.Em),
    color = Gray900
)

private val Heading4 = TextStyle(
    fontFamily = Pretendard,
    fontWeight = FontWeight.Normal,
    fontSize = 20.sp,
    platformStyle = PlatformTextStyle(
        includeFontPadding = false
    ),
//    lineHeight = TextUnit(value = 1.45f, type = TextUnitType.Em),
//    letterSpacing = TextUnit(value = 0.75f, type = TextUnitType.Em),
    color = Gray900
)

private val Body1 = TextStyle(
    fontFamily = Pretendard,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    platformStyle = PlatformTextStyle(
        includeFontPadding = false
    ),
//    lineHeight = TextUnit(value = 1.4f, type = TextUnitType.Em),
//    letterSpacing = TextUnit(value = 0.75f, type = TextUnitType.Em),
)

private val Body2 = TextStyle(
    fontFamily = Pretendard,
    fontWeight = FontWeight.Bold,
    fontSize = 16.sp,
    platformStyle = PlatformTextStyle(
        includeFontPadding = false
    ),
//    lineHeight = TextUnit(value = 1.4f, type = TextUnitType.Em),
//    letterSpacing = TextUnit(value = 0.75f, type = TextUnitType.Em),
    color = Gray900
)

private val Body3 = TextStyle(
    fontFamily = Pretendard,
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp,
    platformStyle = PlatformTextStyle(
        includeFontPadding = false
    ),
//    lineHeight = TextUnit(value = 1.4f, type = TextUnitType.Em),
//    letterSpacing = TextUnit(value = 0.8f, type = TextUnitType.Em),
    color = Gray900
)

private val Body4 = TextStyle(
    fontFamily = Pretendard,
    fontWeight = FontWeight.Bold,
    fontSize = 14.sp,
    platformStyle = PlatformTextStyle(
        includeFontPadding = false
    ),
//    lineHeight = TextUnit(value = 1.4f, type = TextUnitType.Em),
//    letterSpacing = TextUnit(value = 0.8f, type = TextUnitType.Em),
    color = Gray900
)

private val Detail1 = TextStyle(
    fontFamily = Pretendard,
    fontWeight = FontWeight.Medium,
    fontSize = 12.sp,
    platformStyle = PlatformTextStyle(
        includeFontPadding = false
    ),
    lineHeight = 18.sp,
    color = Gray900
)

private val Detail2 = TextStyle(
    fontFamily = Pretendard,
    fontWeight = FontWeight.Bold,
    fontSize = 12.sp,
    platformStyle = PlatformTextStyle(
        includeFontPadding = false
    ),
//    lineHeight = TextUnit(value = 1.4f, type = TextUnitType.Em),
    lineHeight = 18.sp,
    color = Gray900
)

