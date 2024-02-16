package com.sowhat.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sowhat.designsystem.R
import com.sowhat.designsystem.theme.Gray200
import com.sowhat.designsystem.theme.Gray300
import com.sowhat.designsystem.theme.Gray500
import com.sowhat.designsystem.theme.JustSayItTheme

@Composable
fun NoItemNotice(
    modifier: Modifier = Modifier,
    painter: Painter,
    text: String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(JustSayItTheme.Spacing.spaceLg)
    ) {
        Icon(
            modifier = Modifier.size(80.dp),
            tint = Gray300,
            painter = painter,
            contentDescription = "none"
        )

        Text(
            text = text,
            style = JustSayItTheme.Typography.heading4,
            color = Gray500
        )
    }
}

@Preview
@Composable
fun NoItemNoticePreview() {
    NoItemNotice(painter = painterResource(id = R.drawable.ic_pen_24), text = "아직 도착한 알림이 없어요.")
}