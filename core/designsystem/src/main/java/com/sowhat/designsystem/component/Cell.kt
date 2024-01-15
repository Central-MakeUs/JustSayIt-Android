package com.sowhat.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sowhat.designsystem.R
import com.sowhat.designsystem.common.rippleClickable
import com.sowhat.designsystem.theme.JustSayItTheme

@Composable
fun Cell(
    modifier: Modifier = Modifier,
    title: String,
    leadingIcon: Int?,
    trailingIcon: Int?,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .padding(
                horizontal = 16.dp,
                vertical = 6.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.composed {
                onClick?.let {
                    rippleClickable { onClick() }
                } ?: this
            },
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
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(vertical = 10.dp),
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

@Composable
fun Cell(
    modifier: Modifier = Modifier,
    title: String,
    leadingIcon: Int?,
    trailingText: String?,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .padding(
                horizontal = 16.dp,
                vertical = 4.dp
            )
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.composed {
                onClick?.let {
                    rippleClickable { onClick() }
                } ?: this
            },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            leadingIcon?.let {
                Icon(
                    modifier = Modifier.padding(8.dp),
                    painter = painterResource(id = it),
                    contentDescription = "leadingIcon"
                )
            }

            Text(
                modifier = Modifier
                    .padding(vertical = 10.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = title,
                style = JustSayItTheme.Typography.body3,
            )
        }

        trailingText?.let {
            Text(
                modifier = Modifier,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = it,
                style = JustSayItTheme.Typography.body3,
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun CellPreview() {
    Column {
        Cell(
            modifier = Modifier.fillMaxWidth(),
            title = "Camera",
            leadingIcon = R.drawable.ic_camera_24,
            trailingIcon = R.drawable.ic_next_24
        )

        Cell(
            modifier = Modifier.fillMaxWidth(),
            title = "Camera",
            trailingIcon = R.drawable.ic_next_24,
            leadingIcon = null
        )

        Cell(
            modifier = Modifier.fillMaxWidth(),
            title = "앱 버전",
            trailingText = "Ver.1.0",
            leadingIcon = null
        )
    }
}