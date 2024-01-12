package com.sowhat.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sowhat.designsystem.common.bottomBorder
import com.sowhat.designsystem.theme.Gray500
import com.sowhat.designsystem.theme.JustSayItTheme

@Composable
fun DefaultTextField(
    modifier: Modifier = Modifier,
    title: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = JustSayItTheme.Spacing.spaceMedium,
                vertical = JustSayItTheme.Spacing.spaceExtraSmall
            ),
        verticalArrangement = Arrangement.spacedBy(JustSayItTheme.Spacing.spaceExtraSmall)
    ) {
        DefaultTextFieldTitle(title)
        DefaultTextFieldContent(value, onValueChange, placeholder)
    }
}

@Composable
private fun DefaultTextFieldContent(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    BasicTextField(
        modifier = Modifier
            .fillMaxWidth()
            .bottomBorder(
                strokeWidth = 1.dp,
                color = Gray500
            ),
        maxLines = 1,
        value = value,
        onValueChange = onValueChange,
        textStyle = JustSayItTheme.Typography.heading3,
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = JustSayItTheme.Spacing.spaceNormal),
                contentAlignment = Alignment.CenterStart
            ) {
                PlaceholderText(value, placeholder)
                innerTextField()
            }
        }
    )
}

@Composable
private fun DefaultTextFieldTitle(title: String) {
    Text(
        text = title,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = JustSayItTheme.Typography.body1
    )
}

@Composable
private fun PlaceholderText(value: String, placeholder: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        if (value.isEmpty()) {
            Text(
                text = placeholder,
                style = JustSayItTheme.Typography.heading3.copy(color = Gray500)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun TextFieldPreview() {
    var value by rememberSaveable {
        mutableStateOf("")
    }

    DefaultTextField(
        title = "아이디",
        value = value,
        onValueChange = { changedValue -> value = changedValue },
        placeholder = "2자 이상 12자 이하"
    )

}