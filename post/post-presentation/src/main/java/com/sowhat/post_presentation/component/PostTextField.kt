package com.sowhat.post_presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sowhat.designsystem.theme.Gray300
import com.sowhat.designsystem.theme.Gray500
import com.sowhat.designsystem.theme.JustSayItTheme

@Composable
fun PostTextField(
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester,
    text: String,
    placeholder: String,
    onTextChange: (String) -> Unit,
    maxLength: Int
) {
    Box(
        modifier = modifier
            .background(JustSayItTheme.Colors.mainBackground)
            .fillMaxWidth()
            .height(184.dp)
            .clip(JustSayItTheme.Shapes.medium)
            .border(
                width = 1.dp,
                color = Gray300,
                shape = JustSayItTheme.Shapes.medium
            )
    ) {
       Column(
           modifier = Modifier
               .fillMaxSize()
               .padding(JustSayItTheme.Spacing.spaceBase),
           verticalArrangement = Arrangement.SpaceBetween
       ) {
           BasicTextField(
               modifier = Modifier.height(132.dp).fillMaxWidth().focusRequester(focusRequester),
               value = text,
               onValueChange = { changedValue ->
                   if (changedValue.length <= maxLength) onTextChange(changedValue)
               },
               textStyle = JustSayItTheme.Typography.body1
                   .copy(color = JustSayItTheme.Colors.mainTypo),
           )

           TextLengthCounter(
               modifier = Modifier,
               currentLength = text.length.toString(),
               maxLength = maxLength.toString()
           )
       }
    }
}

@Composable
fun TextLengthCounter(
    modifier: Modifier = Modifier,
    currentLength: String,
    maxLength: String,
) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = "$currentLength / $maxLength",
        textAlign = TextAlign.End,
        style = JustSayItTheme.Typography.detail1,
        color = Gray500
    )
}

@Preview
@Composable
fun PostTextFieldPreview() {
    var text by remember {
        mutableStateOf("")
    }

    val maxLength = 300

    PostTextField(
        text = text,
        onTextChange = { text = it },
        maxLength = maxLength,
        placeholder = "내용 입력",
        focusRequester = FocusRequester()
    )
}