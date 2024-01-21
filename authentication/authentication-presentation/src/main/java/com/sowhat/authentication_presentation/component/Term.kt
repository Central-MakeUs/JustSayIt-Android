package com.sowhat.authentication_presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.sowhat.designsystem.theme.JustSayItTheme

@Composable
fun TermText(
    modifier: Modifier = Modifier,
) {
    val annotatedString = buildAnnotatedString {
        withStyle(
            style = JustSayItTheme.Typography.detail1
                .copy(color = JustSayItTheme.Colors.subTypo)
                .toSpanStyle()
        ) {
            append("간편 로그인 시 ")
            withStyle(
                style = JustSayItTheme.Typography.detail2
                    .copy(
                        color = JustSayItTheme.Colors.subTypo,
                        textDecoration = TextDecoration.Underline
                    )
                    .toSpanStyle()
            ) {
                append("이용약관")
            }
            append(", ")
            withStyle(
                style = JustSayItTheme.Typography.detail2
                    .copy(
                        color = JustSayItTheme.Colors.subTypo,
                        textDecoration = TextDecoration.Underline
                    )
                    .toSpanStyle()
            ) {
                append("개인정보 수집 및 이용")
            }
            append("에\n동의하는 것으로 간주합니다.")
        }

        addStringAnnotation(
            tag = "URL",
            annotation = "https://www.notion.so/56e7a2cd2c2746078c11dacf984c941b",
            start = 9,
            end = 13
        )

        addStringAnnotation(
            tag = "URL",
            annotation = "https://www.notion.so/803c054f70bb44398d677780da66f982",
            start = 15,
            end = 27
        )
    }

    val uriHandler = LocalUriHandler.current

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        ClickableText(
            text = annotatedString,
            onClick = {
                annotatedString
                    .getStringAnnotations("URL", it, it)
                    .firstOrNull()?.let { stringAnnotation ->
                        uriHandler.openUri(stringAnnotation.item)
                    }
            },
            style = TextStyle(textAlign = TextAlign.Center, lineHeight = 20.sp)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun TextPreview() {
    TermText(modifier = Modifier.fillMaxWidth())
}