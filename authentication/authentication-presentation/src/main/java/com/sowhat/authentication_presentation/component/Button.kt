package com.sowhat.authentication_presentation.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.sowhat.designsystem.R
import com.sowhat.designsystem.component.DefaultButtonFull
import com.sowhat.designsystem.theme.Gray500
import com.sowhat.designsystem.theme.JustSayItTheme

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun DescButton(
    isValid: Boolean,
    onSubmitClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.basicMarquee(),
            text = stringResource(id = R.string.desc_info_immutable),
            style = JustSayItTheme.Typography.detail1,
            color = Gray500,
            maxLines = 1
        )

        DefaultButtonFull(
            modifier = Modifier
                .padding(
                    top = JustSayItTheme.Spacing.spaceXS,
                    bottom = JustSayItTheme.Spacing.spaceMd,
                    start = JustSayItTheme.Spacing.spaceMd,
                    end = JustSayItTheme.Spacing.spaceMd,
                ),
            text = stringResource(id = R.string.button_start),
            isActive = isValid,
            onClick = onSubmitClick
        )
    }
}
