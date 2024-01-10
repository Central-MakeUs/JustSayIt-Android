package com.sowhat.user_presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.sowhat.designsystem.component.ProfileImageContainer
import com.sowhat.designsystem.theme.JustSayItTheme

@Composable
fun UserProfile(
    modifier: Modifier = Modifier,
    userName: String,
    platformDrawable: Int,
    email: String,
    profileUrl: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = JustSayItTheme.Spacing.spaceMedium),
        horizontalArrangement = Arrangement.spacedBy(JustSayItTheme.Spacing.spaceNormal),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProfileImageContainer(
            modifier = Modifier.weight(0.25f),
            model = profileUrl
        )

        UserInfo(
            modifier = Modifier.weight(0.75f),
            userName = userName,
            platformDrawable = platformDrawable,
            email = email
        )
    }
}

@Composable
private fun UserInfo(
    modifier: Modifier = Modifier,
    userName: String,
    platformDrawable: Int,
    email: String
) {
    Column(
        modifier = modifier
            .padding(vertical = JustSayItTheme.Spacing.spaceLarge),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = userName,
            style = JustSayItTheme.Typography.heading3,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(
            modifier = Modifier
                .height(JustSayItTheme.Spacing.spaceSmall)
        )

        PlatformInfo(platform = platformDrawable, email = email)
    }
}

@Composable
fun PlatformInfo(
    platform: Int,
    email: String,
) {
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = platform),
            contentDescription = null
        )

        Spacer(
            modifier = Modifier
                .width(JustSayItTheme.Spacing.spaceExtraSmall)
        )
        
        Text(
            text = email,
            style = JustSayItTheme.Typography.detail1.copy(
                color = JustSayItTheme.Colors.subTypo
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun UserProfilePreview() {
    UserProfile(
        userName = "kmkimasdfasdafaweawfawewf",
        platformDrawable = com.sowhat.designsystem.R.drawable.ic_naver_16,
        email = "kmkim7575@naver.com",
        profileUrl = "https://github.com/kmkim2689/Android-Wiki/assets/101035437/88d7b249-ad72-4be9-8d79-38dc942e0a7f"
    )
}