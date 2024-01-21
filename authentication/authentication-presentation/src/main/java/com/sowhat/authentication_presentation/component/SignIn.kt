package com.sowhat.authentication_presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sowhat.designsystem.component.LoginIconButton
import com.sowhat.designsystem.theme.Gray300
import com.sowhat.designsystem.theme.Gray600
import com.sowhat.designsystem.theme.JustSayItTheme
import com.sowhat.authentication_presentation.common.SignInPlatform

@Composable
fun SignIn(
    modifier: Modifier = Modifier,
    headerText: String,
    signInPlatforms: List<SignInPlatform>
) {
    Column(
        modifier = modifier
    ) {
        SignInHeader(headerText = headerText)
        SignInButtons(
            signInPlatforms = signInPlatforms
        )
    }
}

@Composable
fun SignInHeader(
    modifier: Modifier = Modifier,
    headerText: String
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .padding(vertical = JustSayItTheme.Spacing.spaceMd),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HeaderLine(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier.weight(1f),
                text = headerText,
                style = JustSayItTheme.Typography.body3,
                textAlign = TextAlign.Center,
                color = Gray600
            )
            HeaderLine(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun SignInButtons(
    modifier: Modifier = Modifier,
    signInPlatforms: List<SignInPlatform>,
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            signInPlatforms.forEach {
                LoginIconButton(
                    iconDrawable = it.iconDrawable,
                    onClick = it.onClick
                )
            }
        }
    }
}

@Composable
fun HeaderLine(modifier: Modifier) {
    Divider(
        modifier = modifier,
        thickness = 1.dp,
        color = Gray300
    )
}

@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun SignInHeaderPreview() {
    SignInHeader(headerText = "간편 로그인")
}