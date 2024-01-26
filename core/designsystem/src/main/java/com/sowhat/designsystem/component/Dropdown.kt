package com.sowhat.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sowhat.designsystem.R
import com.sowhat.designsystem.common.DropdownItem
import com.sowhat.designsystem.common.rippleClickable
import com.sowhat.designsystem.theme.Gray300
import com.sowhat.designsystem.theme.JustSayItTheme

@Composable
fun DropdownHeader(
    modifier: Modifier = Modifier,
    currentMenu: DropdownItem,
    isDropdownExpanded: Boolean,
    onClick: (Boolean) -> Unit
) {

    Box(modifier = modifier
        .rippleClickable { onClick(!isDropdownExpanded) }
    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = JustSayItTheme.Spacing.spaceBase,
                    vertical = JustSayItTheme.Spacing.spaceXS
                ),
            horizontalArrangement = Arrangement
                .spacedBy(JustSayItTheme.Spacing.spaceXXS),
            verticalAlignment = Alignment.CenterVertically
        ) {
            currentMenu.drawable?.let {
                Image(
                    modifier = Modifier.size(JustSayItTheme.Spacing.spaceXL),
                    painter = painterResource(id = currentMenu.drawable),
                    contentDescription = "dropdown_drawable"
                )
            }

            Text(
                text = currentMenu.title,
                color = JustSayItTheme.Colors.mainTypo,
                style = JustSayItTheme.Typography.body2
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_down_16),
                contentDescription = "dropdown_down"
            )
        }

    }
}

@Composable
fun DropdownContents(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    items: List<DropdownItem>,
    onDismiss: () -> Unit,
    onItemClick: (DropdownItem) -> Unit
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
                    width = 1.dp,
                    color = Gray300,
                    shape = JustSayItTheme.Shapes.medium
                ),
            expanded = isVisible,
            onDismissRequest = {
                onDismiss()
            },
        ) {
            DropdownMenus(
                modifier = Modifier,
                items = items,
                onDismiss = onDismiss,
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
fun ColumnScope.DropdownMenus(
    modifier: Modifier = Modifier,
    items: List<DropdownItem>,
    onDismiss: () -> Unit,
    onItemClick: (DropdownItem) -> Unit
) {
    items.forEachIndexed { index, dropdownItem ->
        if (index != 0) Divider(
            thickness = JustSayItTheme.Spacing.spaceTiny,
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
                TextDrawableStart(
                    modifier = Modifier.fillMaxWidth(),
                    text = dropdownItem.title,
                    textStyle = JustSayItTheme.Typography.body3,
                    textColor = JustSayItTheme.Colors.mainTypo,
                    drawable = dropdownItem.drawable,
                    drawableSize = 32.dp
                )
            },
            onClick = {
                onItemClick(dropdownItem)
                onDismiss()
            }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun DropdownPreview() {

    var isVisible by remember {
        mutableStateOf(true)
    }

    DropdownContents(
        isVisible = isVisible,
        items = listOf(
            DropdownItem(title = "Label 1"),
            DropdownItem(title = "Label 2"),
        ),
        onDismiss = { isVisible = false },
        onItemClick = {}
    )
}

@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun DropdownHeaderPreview() {
    DropdownHeader(
        modifier = Modifier,
        currentMenu = DropdownItem(
            title = "행복",
            drawable = R.drawable.ic_happy_96
        ),
        onClick = {},
        isDropdownExpanded = true
    )
}