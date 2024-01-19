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
            contentDescription = "button icon"
        )
    }
}

@Composable
fun LoginIconButton(
    iconDrawable: Int,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Image(
            painter = painterResource(id = iconDrawable),
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
        LoginIconButton(
            iconDrawable = R.drawable.ic_naver_24,
            onClick = {}
        )
    }
}