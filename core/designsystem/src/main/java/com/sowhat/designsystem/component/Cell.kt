package com.sowhat.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sowhat.designsystem.R
import com.sowhat.designsystem.theme.JustSayItTheme

@Composable
fun Cell(
    modifier: Modifier = Modifier,
    title: String,
    leadingIcon: Int?,
    trailingIcon: Int?
) {
    Row(
        modifier = modifier
            .padding(
                horizontal = 16.dp,
                vertical = 4.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            leadingIcon?.let {
                Icon(
                    modifier = Modifier.padding(8.dp),
                    painter = painterResource(id = it),
                    contentDescription = "leadingIcon"
                )
            }

            Text(
                modifier = Modifier.fillMaxWidth(0.9f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = title,
                style = JustSayItTheme.Typography.body3,
            )
        }
        
        trailingIcon?.let {
            Icon(
                painter = painterResource(id = it),
                contentDescription = "leadingIcon"
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun CellPreview() {
    Cell(
        modifier = Modifier.fillMaxWidth(),
        title = "Camera",
        leadingIcon = R.drawable.ic_camera_24,
        trailingIcon = R.drawable.ic_next_24
    )
}