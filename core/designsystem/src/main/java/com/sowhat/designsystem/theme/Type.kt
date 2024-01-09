package com.sowhat.designsystem.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.sp
import com.sowhat.designsystem.R

data class JustSayItTypography(
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

private val HeadlineR = TextStyle(
    fontFamily = Pretendard,
    fontWeight = FontWeight.Normal,
    fontSize = 24.sp,
    lineHeight = TextUnit(value = 1.4f, type = TextUnitType.Em),
    letterSpacing = TextUnit(value = 0.75f, type = TextUnitType.Em)
)

private val Heading2 = TextStyle(
    fontFamily = Pretendard,
    fontWeight = FontWeight.Normal,
    fontSize = 22.sp,
    lineHeight = TextUnit(value = 1.45f, type = TextUnitType.Em),
    letterSpacing = TextUnit(value = 0.75f, type = TextUnitType.Em)
)

private val Heading3 = TextStyle(
    fontFamily = Pretendard,
    fontWeight = FontWeight.Normal,
    fontSize = 20.sp,
    lineHeight = TextUnit(value = 1.4f, type = TextUnitType.Em),
    letterSpacing = TextUnit(value = 0.75f, type = TextUnitType.Em)
)

private val Heading4 = TextStyle(
    fontFamily = Pretendard,
    fontWeight = FontWeight.Normal,
    fontSize = 20.sp,
    lineHeight = TextUnit(value = 1.45f, type = TextUnitType.Em),
    letterSpacing = TextUnit(value = 0.75f, type = TextUnitType.Em)
)

private val Body1 = TextStyle(
    fontFamily = Pretendard,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    lineHeight = TextUnit(value = 1.4f, type = TextUnitType.Em),
    letterSpacing = TextUnit(value = 0.75f, type = TextUnitType.Em)
)

private val Body2 = TextStyle(
    fontFamily = Pretendard,
    fontWeight = FontWeight.Bold,
    fontSize = 16.sp,
    lineHeight = TextUnit(value = 1.4f, type = TextUnitType.Em),
    letterSpacing = TextUnit(value = 0.75f, type = TextUnitType.Em)
)

private val Body3 = TextStyle(
    fontFamily = Pretendard,
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp,
    lineHeight = TextUnit(value = 1.4f, type = TextUnitType.Em),
    letterSpacing = TextUnit(value = 0.8f, type = TextUnitType.Em)
)

private val Body4 = TextStyle(
    fontFamily = Pretendard,
    fontWeight = FontWeight.Bold,
    fontSize = 14.sp,
    lineHeight = TextUnit(value = 1.4f, type = TextUnitType.Em),
    letterSpacing = TextUnit(value = 0.8f, type = TextUnitType.Em)
)

private val Detail1 = TextStyle(
    fontFamily = Pretendard,
    fontWeight = FontWeight.Medium,
    fontSize = 12.sp,
    lineHeight = TextUnit(value = 1.4f, type = TextUnitType.Em),
    letterSpacing = TextUnit(value = 0.8f, type = TextUnitType.Em)
)

private val Detail2 = TextStyle(
    fontFamily = Pretendard,
    fontWeight = FontWeight.Bold,
    fontSize = 12.sp,
    lineHeight = TextUnit(value = 1.4f, type = TextUnitType.Em),
    letterSpacing = TextUnit(value = 0.8f, type = TextUnitType.Em)
)

