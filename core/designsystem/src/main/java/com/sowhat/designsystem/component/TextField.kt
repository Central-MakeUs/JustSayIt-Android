package com.sowhat.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
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
                horizontal = JustSayItTheme.Spacing.spaceBase,
                vertical = JustSayItTheme.Spacing.spaceBase
            ),
        verticalArrangement = Arrangement.spacedBy(JustSayItTheme.Spacing.spaceXXS)
    ) {
        DefaultHeader(title)
        DefaultTextFieldContent(value, onValueChange, placeholder)
    }
}

//@Composable
//fun OutlinedTextFieldDefault(
//    modifier: Modifier = Modifier,
//    placeholder: String,
//    value: String,
//    onValueChange: (String) -> Unit,
//) {
//    Box(
//        modifier = modifier
//            .fillMaxWidth()
//            .height(44.dp)
//            .clip(JustSayItTheme.Shapes.medium)
//            .border(width = 1.dp, ),
//
//    )
//}

@Composable
fun OutlinedDefaultTextField(
    modifier: Modifier = Modifier,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        placeholder = {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = placeholder,
                textAlign = TextAlign.Center,
                style = JustSayItTheme.Typography.body1,
                color = Gray500
            )
        },
        textStyle = JustSayItTheme.Typography.body1.copy(textAlign = TextAlign.Center),
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        shape = JustSayItTheme.Shapes.medium,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = JustSayItTheme.Colors.mainTypo,
            unfocusedTextColor = JustSayItTheme.Colors.mainTypo,
            focusedPlaceholderColor = Gray500,
            unfocusedPlaceholderColor = Gray500,
            focusedContainerColor = JustSayItTheme.Colors.mainSurface,
            unfocusedContainerColor = JustSayItTheme.Colors.mainSurface,
            cursorColor = Color.Transparent,
            errorCursorColor = Color.Transparent,
            focusedBorderColor = JustSayItTheme.Colors.mainTypo,
            unfocusedBorderColor = Gray500
        ),
    )
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
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        maxLines = 1,
        value = value,
        singleLine = true,
        onValueChange = onValueChange,
        textStyle = JustSayItTheme.Typography.heading3
            .copy(color = JustSayItTheme.Colors.mainTypo),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = JustSayItTheme.Spacing.spaceSm),
                contentAlignment = Alignment.CenterStart
            ) {
                PlaceholderText(value, placeholder)
                innerTextField()
            }
        }
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

@Preview(showBackground = true)
@Composable
fun OutlinedTextFieldPreview() {

    var text by remember {
        mutableStateOf("")
    }

    OutlinedDefaultTextField(
        placeholder = "YYYY",
        value = text,
        onValueChange = { text = it },
    )
}