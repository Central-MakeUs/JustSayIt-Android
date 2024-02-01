package com.sowhat.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.sowhat.designsystem.R
import com.sowhat.designsystem.common.rippleClickable
import com.sowhat.designsystem.theme.Gray200
import com.sowhat.designsystem.theme.Gray400
import com.sowhat.designsystem.theme.JustSayItTheme

@Composable
fun ProfileImage(
    modifier: Modifier = Modifier,
    profileUri: Any?,
    badgeDrawable: Int?,
    badgeBackgroundColor: Color,
    badgeIconTint: Color,
    isMenuVisible: Boolean,
    onMenuShowChange: (Boolean) -> Unit,
    onChooseClick: () -> Unit,
    onResetClick: () -> Unit,
    onMenuDismiss: () -> Unit
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = 40.dp,
                bottom = 20.dp
            )
            .background(JustSayItTheme.Colors.mainBackground)
    ) {
        val (profile, badge) = createRefs()

        SquaredImageContainer(
            modifier = Modifier
                .fillMaxWidth(0.25f)
                .constrainAs(profile) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            model = profileUri ?: "https://github.com/Central-MakeUs/JustSayIt-Android/assets/101035437/9c7a1028-c783-4251-99cd-f5f4d8d0b251"
        )

        Box(
            modifier = Modifier
                .size(40.dp)
                .constrainAs(badge) {
                    end.linkTo(profile.end, (-15).dp)
                    bottom.linkTo(profile.bottom)
                },
            contentAlignment = Alignment.BottomEnd
        ){
            Badge(
                modifier = Modifier
                    .clip(shape = JustSayItTheme.Shapes.circle)
                    .fillMaxSize()
                    .rippleClickable {
                        if (profileUri == null) onChooseClick() else onMenuShowChange(!isMenuVisible)
                    },
                backgroundColor = badgeBackgroundColor,
                iconTint = badgeIconTint,
                drawable = badgeDrawable
            )

            PopupMenuContents(
                isVisible = isMenuVisible,
                items = listOf(
                    PopupMenuItem(
                        title = stringResource(id = R.string.popup_choose_picture),
                        drawable = R.drawable.ic_photo_20,
                        onItemClick = onChooseClick,
                        contentColor = JustSayItTheme.Colors.mainTypo
                    ),
                    PopupMenuItem(
                        title = stringResource(id = R.string.popup_delete_picture),
                        drawable = R.drawable.ic_delete_20,
                        onItemClick = onResetClick,
                        contentColor = JustSayItTheme.Colors.error
                    )
                ),
                onDismiss = onMenuDismiss,
                onItemClick = { popupMenu ->
                    popupMenu.onItemClick?.let { it() }
                }
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun ProfileImagePreview() {
    ProfileImage(
        profileUri = "https://i.stack.imgur.com/6C9Qv.png",
        badgeDrawable = R.drawable.ic_camera_24,
        badgeBackgroundColor = Gray200,
        badgeIconTint = Gray400,
        isMenuVisible = true,
        onMenuShowChange = {},
        onChooseClick = {},
        onResetClick = {},
        onMenuDismiss = {}
    )
}