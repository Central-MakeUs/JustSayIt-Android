package com.sowhat.post_presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sowhat.designsystem.component.Toggle
import com.sowhat.designsystem.theme.JustSayItTheme
import com.sowhat.post_presentation.common.SubjectItem

@Composable
fun PostToggle(
    modifier: Modifier = Modifier,
    subject: SubjectItem,
    isActivated: Boolean,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(JustSayItTheme.Spacing.spaceBase)
            .composed {
                if (!isActivated) {
                    alpha(0.3f)
                } else this
            },
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        PostSubject(
            title = subject.title,
            isActivated = isActivated,
            subTitle = subject.subTitle
        )

        Toggle(
            modifier = Modifier.padding(4.dp),
            isChecked = isChecked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Preview
@Composable
fun PostTogglePreview() {
    var isSelected by remember {
        mutableStateOf(false)
    }

    PostToggle(
        modifier = Modifier.background(Color.White),
        subject = SubjectItem("공개", "공개 여부를 결정"),
        isActivated = true,
        isChecked = isSelected,
        onCheckedChange = { isSelected = it }
    )
}