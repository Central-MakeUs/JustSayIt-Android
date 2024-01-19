package com.sowhat.post_presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.sowhat.designsystem.theme.JustSayItTheme
import com.sowhat.post_presentation.common.SubjectItem

@Composable
fun PostText(
    modifier: Modifier = Modifier,
    subject: SubjectItem,
    text: String,
    placeholder: String,
    onTextChange: (String) -> Unit,
    maxLength: Int
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(JustSayItTheme.Spacing.spaceMedium),
        verticalArrangement = Arrangement
            .spacedBy(JustSayItTheme.Spacing.spaceNormal)
    ) {
        PostSubject(
            modifier = Modifier.fillMaxWidth(),
            title = subject.title,
            subTitle = subject.subTitle
        )

        PostTextField(
            text = text,
            placeholder = placeholder,
            onTextChange = onTextChange,
            maxLength = maxLength
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PostTextPreview() {
    var text by remember {
        mutableStateOf("")
    }

    PostText(
        modifier = Modifier.background(Color.White),
        subject = SubjectItem("글쓰기", "글을 씁니다."),
        text = text,
        onTextChange = { text = it },
        maxLength = 300,
        placeholder = "내용을 작성해주세요."
    )
}