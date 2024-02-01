package com.sowhat.authentication_presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import com.sowhat.designsystem.component.DefaultHeader
import com.sowhat.designsystem.component.OutlinedDefaultTextField
import com.sowhat.designsystem.theme.JustSayItTheme
import com.sowhat.authentication_presentation.common.TextFieldInfo

@Composable
fun DobTextField(
    modifier: Modifier = Modifier,
    title: String,
    items: List<TextFieldInfo>,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(JustSayItTheme.Spacing.spaceBase)
            .background(JustSayItTheme.Colors.mainBackground),
        verticalArrangement = Arrangement.spacedBy(JustSayItTheme.Spacing.spaceSm)
    ) {
        DefaultHeader(title = title)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(JustSayItTheme.Colors.mainBackground)
        ) {
            items.forEachIndexed { index, item ->
                if (index != 0) Spacer(modifier = Modifier.width(JustSayItTheme.Spacing.spaceXXS))

                OutlinedDefaultTextField(
                    modifier = Modifier.weight(1f).composed {
                        item.focusRequester?.let {
                            it
                        } ?: this
                    },
                    placeholder = item.placeholder,
                    value = item.value,
                    onValueChange = item.onValueChange,
                    onNext = item.onNext
                )
            }
        }
    }
}