package com.sowhat.user_presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sowhat.designsystem.component.Cell
import com.sowhat.designsystem.component.Header
import com.sowhat.designsystem.theme.JustSayItTheme
import com.sowhat.user_presentation.common.MenuItem

@Composable
fun Menu(
    modifier: Modifier = Modifier,
    title: String,
    menus: List<MenuItem>
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Header(
            modifier = Modifier.fillMaxWidth(),
            title = title
        )

        MenuItems(
            modifier = Modifier.fillMaxWidth(),
            menus = menus
        )
    }
}

@Composable
private fun MenuItems(
    modifier: Modifier = Modifier,
    menus: List<MenuItem>
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(JustSayItTheme.Spacing.spaceXXS)
    ) {
        menus.forEach { menuItem ->
            if (menuItem.trailingIcon != null && menuItem.trailingText == null) {
                Cell(
                    modifier = Modifier.fillMaxWidth(),
                    title = menuItem.title,
                    leadingIcon = menuItem.leadingIcon,
                    trailingIcon = menuItem.trailingIcon,
                    onClick = menuItem.onClick
                )
            } else if (menuItem.trailingIcon == null && menuItem.trailingText != null) {
                Cell(
                    modifier = Modifier.fillMaxWidth(),
                    title = menuItem.title,
                    leadingIcon = menuItem.leadingIcon,
                    trailingText = menuItem.trailingText,
                    onClick = menuItem.onClick
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun MenuPreview() {
    Menu(
        title = "일반",
        menus = listOf(
            MenuItem(title = "개발자에게 연락하기", trailingIcon = com.sowhat.designsystem.R.drawable.ic_next_24),
            MenuItem(title = "이용 약관", trailingIcon = com.sowhat.designsystem.R.drawable.ic_next_24),
            MenuItem(title = "개인정보 보호", trailingIcon = com.sowhat.designsystem.R.drawable.ic_next_24),
            MenuItem(title = "앱 버전", trailingText = "Ver.1.0"),
        )
    )
}