package com.sowhat.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sowhat.designsystem.common.normalShadow
import com.sowhat.designsystem.theme.JustSayItTheme

@Composable
fun Dropdown(
    isExpanded: Boolean,
    items: List<DropdownItem>,
    onDismiss: () -> Unit,
) {
    DropdownMenu(
        modifier = Modifier
            .background(JustSayItTheme.Colors.subBackground)
            .normalShadow(),
        expanded = isExpanded,
        onDismissRequest = {
            onDismiss()
        },
    ) {
        items.forEach { dropdownItem ->
            DropdownMenuItem(
                text = {
                    Text(
                        text = dropdownItem.title,
                        style = JustSayItTheme.Typography.body3
                    )
                },
                onClick = {
                    dropdownItem.onItemClick()
                    onDismiss()
                }
            )
        }
    }
}

data class DropdownItem(
    val title: String,
    val onItemClick: () -> Unit
)

@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun DropdownPreview() {
    Dropdown(
        isExpanded = true,
        items = listOf(
            DropdownItem(title = "Label 1", onItemClick = {}),
            DropdownItem(title = "Label 2", onItemClick = {}),
        ),
        onDismiss = {})
}