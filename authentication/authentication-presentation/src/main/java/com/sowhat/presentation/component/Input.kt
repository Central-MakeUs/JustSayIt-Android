package com.sowhat.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sowhat.designsystem.component.DefaultHeader
import com.sowhat.designsystem.component.OutlinedDefaultTextField
import com.sowhat.designsystem.theme.JustSayItTheme
import com.sowhat.presentation.common.TextFieldInfo

@Composable
fun DobTextField(
    modifier: Modifier = Modifier,
    title: String,
    items: List<TextFieldInfo>
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(JustSayItTheme.Spacing.spaceMedium),
        verticalArrangement = Arrangement.spacedBy(JustSayItTheme.Spacing.spaceNormal)
    ) {
        DefaultHeader(title = title)

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            items.forEachIndexed { index, item ->
                if (index != 0) Spacer(modifier = Modifier.width(JustSayItTheme.Spacing.spaceExtraSmall))

                OutlinedDefaultTextField(
                    modifier = Modifier.weight(1f),
                    placeholder = item.placeholder,
                    value = item.value,
                    onValueChange = item.onValueChange
                )
            }
        }
    }
}