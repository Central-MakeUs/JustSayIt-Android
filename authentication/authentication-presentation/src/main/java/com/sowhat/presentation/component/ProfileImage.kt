package com.sowhat.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.sowhat.designsystem.common.rippleClickable
import com.sowhat.designsystem.component.Badge
import com.sowhat.designsystem.component.ProfileImageContainer
import com.sowhat.designsystem.theme.Gray200
import com.sowhat.designsystem.theme.Gray400

@Composable
fun ProfileImage(
    modifier: Modifier = Modifier,
    profileUri: Any?,
    badgeDrawable: Int?,
    badgeBackgroundColor: Color,
    badgeIconTint: Color,
    onClick: () -> Unit
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = 40.dp,
                bottom = 20.dp
            )
    ) {
        val (profile, badge) = createRefs()

        ProfileImageContainer(
            modifier = Modifier
                .fillMaxWidth(0.25f)
                .rippleClickable { onClick() }
                .constrainAs(profile) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            model = profileUri
        )

        Badge(
            modifier = Modifier
                .size(40.dp)
                .rippleClickable { onClick() }
                .constrainAs(badge) {
                    end.linkTo(profile.end, (-15).dp)
                    bottom.linkTo(profile.bottom)
                },
            backgroundColor = badgeBackgroundColor,
            iconTint = badgeIconTint,
            drawable = badgeDrawable
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun ProfileImagePreview() {
    ProfileImage(
        profileUri = "https://i.stack.imgur.com/6C9Qv.png",
        badgeDrawable = com.sowhat.designsystem.R.drawable.ic_camera_24,
        badgeBackgroundColor = Gray200,
        badgeIconTint = Gray400,
        onClick = {}
    )
}