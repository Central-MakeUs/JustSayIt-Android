package com.sowhat.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sowhat.designsystem.common.DropdownItem
import com.sowhat.designsystem.theme.Gray300
import com.sowhat.designsystem.theme.JustSayItTheme

@Composable
fun PopupMenuContents(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    items: List<PopupMenuItem>,
    onDismiss: () -> Unit,
    onItemClick: (PopupMenuItem) -> Unit
) {
    // https://stackoverflow.com/questions/66781028/jetpack-compose-dropdownmenu-with-rounded-corners
    MaterialTheme(
        shapes = MaterialTheme.shapes.copy(extraSmall = RoundedCornerShape(12.dp)),
        colorScheme = MaterialTheme.colorScheme.copy(surface = JustSayItTheme.Colors.mainSurface)
    ) {
        DropdownMenu(
            modifier = modifier
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .background(
                    color = JustSayItTheme.Colors.mainSurface,
                )
                .border(
                    width = JustSayItTheme.Spacing.border,
                    color = Gray300,
                    shape = JustSayItTheme.Shapes.medium
                ),
            expanded = isVisible,
            onDismissRequest = {
                onDismiss()
            },
        ) {
            PopupMenuItems(
                modifier = Modifier,
                items = items,
                onDismiss = onDismiss,
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
fun ColumnScope.PopupMenuItems(
    modifier: Modifier = Modifier,
    items: List<PopupMenuItem>,
    onDismiss: () -> Unit,
    onItemClick: (PopupMenuItem) -> Unit
) {
    items.forEachIndexed { index, dropdownItem ->

        val textColor = dropdownItem.contentColor ?: JustSayItTheme.Colors.mainTypo

        if (index != 0) Divider(
            thickness = JustSayItTheme.Spacing.border,
            color = Gray300,
        )

        DropdownMenuItem(
            modifier = modifier
                .wrapContentSize()
                .background(
                    color = JustSayItTheme.Colors.mainSurface,
                )
                .padding(vertical = 0.dp),
            text = {
                TextWithIconStart(
                    modifier = Modifier.fillMaxWidth(),
                    text = dropdownItem.title,
                    textStyle = JustSayItTheme.Typography.body3,
                    textColor = textColor,
                    iconDrawable = dropdownItem.drawable,
                    drawableSize = JustSayItTheme.Spacing.spaceLg
                )
            },
            onClick = {
                onItemClick(dropdownItem)
                onDismiss()
            }
        )
    }
}


data class PopupMenuItem(
    val title: String,
    val drawable: Int? = null,
    val postData: Long? = null,
    val onItemClick: (() -> Unit)? = null,
    val contentColor: Color? = null
) {
    constructor(
        title: String,
        drawable: Int,
        onItemClick: () -> Unit
    ): this(
        title = title,
        drawable = drawable,
        onItemClick = onItemClick,
        postData = null
    )

    constructor(
        title: String,
        drawable: Int,
        onItemClick: () -> Unit,
        contentColor: Color
    ): this(
        title = title,
        drawable = drawable,
        onItemClick = onItemClick,
        postData = null,
        contentColor = contentColor
    )

    constructor(
        title: String,
        onItemClick: () -> Unit
    ): this(
        title = title,
        onItemClick = onItemClick,
        postData = null,
        drawable = null
    )

    constructor(
        title: String,
        onItemClick: () -> Unit,
        drawable: Int
    ): this(
        title = title,
        onItemClick = onItemClick,
        drawable = drawable,
        postData = null
    )
}
