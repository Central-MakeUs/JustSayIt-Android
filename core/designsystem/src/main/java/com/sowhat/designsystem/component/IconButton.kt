package com.sowhat.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.sowhat.designsystem.R
import com.sowhat.designsystem.theme.JustSayItTheme

@Composable
fun DefaultIconButton(
    modifier: Modifier = Modifier,
    iconDrawable: Int,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Icon(
            painter = painterResource(id = iconDrawable),
            contentDescription = "button icon",
            tint = JustSayItTheme.Colors.mainTypo
        )
    }
}

@Composable
fun ImageButton(
    modifier: Modifier = Modifier,
    imageDrawable: Int,
    onClick: () -> Unit,
    enabled: Boolean = true,
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled
    ) {
        Image(
            painter = painterResource(id = imageDrawable),
            contentDescription = "login icon"
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun DefaultIconButtonPreview() {
    Column {
        DefaultIconButton(
            iconDrawable = R.drawable.ic_camera_24,
            onClick = {}
        )
        ImageButton(
            imageDrawable = R.drawable.ic_naver_24,
            onClick = {}
        )
    }
}