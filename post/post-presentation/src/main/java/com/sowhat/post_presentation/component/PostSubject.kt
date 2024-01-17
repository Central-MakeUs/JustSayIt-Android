package com.sowhat.post_presentation.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.sowhat.designsystem.theme.Gray700
import com.sowhat.designsystem.theme.JustSayItTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PostSubject(
    modifier: Modifier = Modifier,
    isActivated: Boolean = true,
    title: String,
    subTitle: String
) {
    val inActiveTextColor = JustSayItTheme.Colors.subTypo

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(JustSayItTheme.Spacing.spaceExtraSmall)
    ) {
        Text(
            text = title,
            maxLines = 1,
            style = JustSayItTheme.Typography.body2,
            color = if (isActivated) JustSayItTheme.Colors.mainTypo else inActiveTextColor
        )
        
        Text(
            modifier = Modifier.basicMarquee(),
            text = subTitle,
            maxLines = 1,
            style = JustSayItTheme.Typography.detail1,
            color = if (isActivated) Gray700 else inActiveTextColor
        )
    }
}