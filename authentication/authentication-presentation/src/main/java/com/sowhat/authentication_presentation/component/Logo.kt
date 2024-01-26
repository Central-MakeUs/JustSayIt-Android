package com.sowhat.authentication_presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.sowhat.designsystem.theme.JustSayItTheme

@Composable
fun Logo(
    modifier: Modifier = Modifier,
    title: String,
    subTitle: String,
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Subtitle(subTitle = subTitle)
        Title(title = title)
    }
}

@Composable
private fun Title(title: String) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = JustSayItTheme.Spacing.spaceBase,
                    vertical = JustSayItTheme.Spacing.spaceXS
                ),
            text = title,
            style = JustSayItTheme.Typography.headlineB,
            textAlign = TextAlign.Center,
            color = JustSayItTheme.Colors.mainTypo
        )
    }
}

@Composable
private fun Subtitle(subTitle: String) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = JustSayItTheme.Spacing.spaceBase,
                    vertical = JustSayItTheme.Spacing.spaceXS
                ),
            text = subTitle,
            style = JustSayItTheme.Typography.body4,
            textAlign = TextAlign.Center,
            color = JustSayItTheme.Colors.mainTypo
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun LogoPreview() {
    Logo(title = "그냥, 그렇다고", subTitle = "(서비스 한줄 소개)")
}