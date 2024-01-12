package com.sowhat.designsystem.component

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sowhat.designsystem.common.rippleClickable
import com.sowhat.designsystem.theme.Gray200
import com.sowhat.designsystem.theme.JustSayItTheme

@Composable
fun Tab(
    modifier: Modifier = Modifier,
    selectedItem: String,
    items: List<String>,
    selectedColor: Color,
    unselectedColor: Color,
    onSelectedItemChange: (String) -> Unit
) {
    // https://tourspace.tistory.com/416
    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
    ) {
        items.forEachIndexed { index, tabItem ->
            val isSelected = tabItem == selectedItem

            if (index != 0) {
                Divider(
                    modifier = Modifier
                        .padding(vertical = JustSayItTheme.Spacing.spaceSmall)
                        .width(0.5.dp)
                        .fillMaxHeight(),
                    color = Gray200
                )
            }

            Box(
                modifier = Modifier
                    .rippleClickable {
                        onSelectedItemChange(tabItem)
                    }
            ) {
                Text(
                    modifier = Modifier
                        .padding(
                            horizontal = JustSayItTheme.Spacing.spaceMedium,
                            vertical = JustSayItTheme.Spacing.spaceSmall
                        ),
                    text = tabItem,
                    style = JustSayItTheme.Typography.detail2
                        .copy(color = if (isSelected) selectedColor else unselectedColor)
                )
            }
        }
    }
}



@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun TabPreview() {
    val items = listOf(
        "kmkim", "kkm"
    )

    var selectedItem by remember {
        mutableStateOf(items[0])
    }

    Tab(
        selectedItem = selectedItem,
        items = items,
        selectedColor = Color.Black,
        unselectedColor = Color.Gray,
        onSelectedItemChange = { newItem -> selectedItem = newItem }
    )
}