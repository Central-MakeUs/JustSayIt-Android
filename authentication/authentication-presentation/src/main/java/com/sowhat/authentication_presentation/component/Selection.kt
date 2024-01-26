package com.sowhat.authentication_presentation.component

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
import androidx.compose.ui.tooling.preview.Preview
import com.sowhat.designsystem.component.DefaultButtonDual
import com.sowhat.designsystem.component.DefaultHeader
import com.sowhat.designsystem.theme.JustSayItTheme

@Composable
fun Selection(
    modifier: Modifier = Modifier,
    title: String,
    buttons: List<String>,
    activeButton: String?,
    onClick: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(JustSayItTheme.Spacing.spaceBase)
            .background(JustSayItTheme.Colors.mainBackground),
        verticalArrangement = Arrangement.spacedBy(JustSayItTheme.Spacing.spaceSm)
    ) {
        DefaultHeader(title = title)

        DefaultButtonDual(
            buttons = buttons,
            activeButton = activeButton,
            onClick = onClick
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun SelectionPreview() {

    val items = listOf("남", "여")

    var selectedItem by remember {
        mutableStateOf(items.first())
    }

    Selection(
        title = "성별",
        buttons = items,
        activeButton = selectedItem,
        onClick = { selected -> selectedItem = selected }
    )
}