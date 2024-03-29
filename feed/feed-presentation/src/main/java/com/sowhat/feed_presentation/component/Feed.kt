package com.sowhat.feed_presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.sowhat.database.entity.EntireFeedEntity
import com.sowhat.common.util.toDate
import com.sowhat.designsystem.common.MoodItem
import com.sowhat.designsystem.common.bottomBorder
import com.sowhat.designsystem.component.ChipSm
import com.sowhat.designsystem.component.DefaultIconButton
import com.sowhat.designsystem.component.ImageContainer
import com.sowhat.designsystem.component.PopupMenuContents
import com.sowhat.designsystem.component.PopupMenuItem
import com.sowhat.designsystem.component.TimelineFeedImages
import com.sowhat.designsystem.theme.Gray300
import com.sowhat.designsystem.theme.Gray400
import com.sowhat.designsystem.theme.JustSayItTheme

@Composable
fun Feed(
    modifier: Modifier = Modifier,
    feedItem: EntireFeedEntity,
    selectedSympathy: MoodItem?,
    sympathyItems: List<MoodItem>,
    // 취소 시 null을 보내고, 선택 시 아이템을 보내서 외부의 selectedSympathy의 값 변경
    onSympathyItemClick: (MoodItem?) -> Unit,
    onMenuClick: () -> Unit,
    onFeedClick: () -> Unit,
    isMenuForOwnerVisible: Boolean,
    isMenuForNotOwnerVisible: Boolean,
    popupMenuForOwner: List<PopupMenuItem>,
    popupMenuForNotOwner: List<PopupMenuItem>,
    onMenuDismiss: () -> Unit,
    onMenuItemClick: (PopupMenuItem) -> Unit,
    onImageClick: (String) -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = JustSayItTheme.Colors.mainBackground)
            // bottomBorder 순서가 padding 앞에 와야 경계선이 아이템의 최하단에 형성
            .bottomBorder(JustSayItTheme.Spacing.border, Gray300, JustSayItTheme.Spacing.spaceBase)
            .padding(vertical = JustSayItTheme.Spacing.spaceBase),
        verticalArrangement = Arrangement
            .spacedBy(JustSayItTheme.Spacing.spaceBase)
    ) {
        FeedProfile(
            modifier = Modifier.padding(start = JustSayItTheme.Spacing.spaceBase),
            feedItem = feedItem,
            onMenuClick = onMenuClick,
            isMenuForOwnerVisible = isMenuForOwnerVisible,
            isMenuForNotOwnerVisible = isMenuForNotOwnerVisible,
            popupMenuForOwner = popupMenuForOwner,
            popupMenuForNotOwner = popupMenuForNotOwner,
            onDismiss = onMenuDismiss,
            onMenuItemClick = onMenuItemClick
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = JustSayItTheme.Spacing.spaceBase),
            verticalArrangement = Arrangement
                .spacedBy(JustSayItTheme.Spacing.spaceBase)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = feedItem.bodyText,
                style = JustSayItTheme.Typography.body1,
                color = JustSayItTheme.Colors.mainTypo
            )

            if (feedItem.photo.isNotEmpty()) {
                TimelineFeedImages(
                    models = feedItem.photo,
                    onImageClick = onImageClick
                )
            }
        }

        Column {
            SympathyChips(
                currentMood = selectedSympathy,
                onChange = onSympathyItemClick,
                availableItems = sympathyItems,
                isOwner = feedItem.isOwner ?: return
            )
        }
    }
}

@Composable
fun FeedProfile(
    modifier: Modifier = Modifier,
    feedItem: EntireFeedEntity,
    onMenuClick: () -> Unit,
    isMenuForOwnerVisible: Boolean,
    isMenuForNotOwnerVisible: Boolean,
    popupMenuForOwner: List<PopupMenuItem>,
    popupMenuForNotOwner: List<PopupMenuItem>,
    onDismiss: () -> Unit,
    onMenuItemClick: (PopupMenuItem) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        FeedProfileInfo(
            profileUrl = feedItem.profileImg,
            nickname = feedItem.nickname,
            date = feedItem.createdAt.toDate()
        )

        Box(
            modifier = Modifier.height(24.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            DefaultIconButton(
                modifier = Modifier,
                iconDrawable = com.sowhat.designsystem.R.drawable.ic_more_20,
                onClick = onMenuClick
            )

            Box(
                modifier = Modifier
                    .padding(end = JustSayItTheme.Spacing.spaceSm)
            ) {
                PopupMenuContents(
                    modifier = Modifier,
                    isVisible = isMenuForOwnerVisible,
                    items = popupMenuForOwner,
                    onDismiss = onDismiss,
                    onItemClick = onMenuItemClick
                )

                PopupMenuContents(
                    modifier = Modifier,
                    isVisible = isMenuForNotOwnerVisible,
                    items = popupMenuForNotOwner,
                    onDismiss = onDismiss,
                    onItemClick = onMenuItemClick
                )
            }

        }
    }
}

@Composable
fun FeedProfileInfo(
    modifier: Modifier = Modifier,
    profileUrl: Any?,
    nickname: String,
    date: String,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        FeedProfileImage(modifier = Modifier.size(24.dp), model = profileUrl)
        
        Spacer(modifier = Modifier.width(JustSayItTheme.Spacing.spaceXS))

        Text(
            text = nickname,
            style = JustSayItTheme.Typography.body3,
            color = JustSayItTheme.Colors.mainTypo
        )

        Spacer(modifier = Modifier.width(JustSayItTheme.Spacing.spaceXXS))

        Text(
            text = date,
            style = JustSayItTheme.Typography.detail1,
            color = Gray400
        )
    }
}

@Composable
fun FeedProfileImage(
    modifier: Modifier = Modifier,
    model: Any?,
) {
    ImageContainer(
        modifier = modifier,
        borderWidth = 1.dp,
        borderColor = Gray300,
        shape = JustSayItTheme.Shapes.circle,
        model = model,
        contentDescription = "profile_image"
    )
}
@Composable
fun SympathyChips(
    modifier: Modifier = Modifier,
    currentMood: MoodItem?,
    onChange: (MoodItem?) -> Unit,
    availableItems: List<MoodItem>?,
    isOwner: Boolean
) {
    val collapse = currentMood != null

    if (!availableItems.isNullOrEmpty()) {
        Box(
            modifier = modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            SelectedSympathy(
                availableItems,
                currentMood,
                collapse,
                onChange,
                isOwner
            )

            UnselectedList(
                modifier,
                availableItems,
                currentMood,
                collapse,
                onChange,
                isOwner
            )
        }
    }
}


@Composable
private fun UnselectedList(
    modifier: Modifier,
    availableItems: List<MoodItem>,
    currentMood: MoodItem?,
    collapse: Boolean,
    onChange: (MoodItem?) -> Unit,
    isOwner: Boolean
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .zIndex(0.5f),
        horizontalArrangement = Arrangement.spacedBy(
            alignment = Alignment.End,
            space = JustSayItTheme.Spacing.spaceXS
        ),
    ) {
        item {
            Spacer(modifier = Modifier.width(JustSayItTheme.Spacing.spaceXS))
        }

        itemsIndexed(availableItems) { index, item ->
            val isSelected = currentMood == item
            AnimatedVisibility(
                visible = !collapse,
                enter = slideInHorizontally() { fullWidth ->
                    fullWidth * (availableItems.size / (index + 1))
                } + fadeIn(),
                exit = slideOutHorizontally() { fullWidth ->
                    fullWidth * (availableItems.size - index - 1)
                } + fadeOut()
            ) {
                ChipSm(
                    moodItem = item,
                    isSelected = isSelected,
                    onClick = { item: MoodItem ->
                        if (!isSelected) onChange(item) else onChange(null)
                    },
                )
            }
        }

        item {
            Spacer(modifier = Modifier.width(JustSayItTheme.Spacing.spaceXS))
        }
    }
}

@Composable
private fun SelectedSympathy(
    availableItems: List<MoodItem>,
    currentMood: MoodItem?,
    collapse: Boolean,
    onChange: (MoodItem?) -> Unit,
    isOwner: Boolean
) {
    availableItems.forEachIndexed { index, item ->
        val isSelected = currentMood == item

        AnimatedVisibility(
            modifier = Modifier
                .zIndex(1f)
                .padding(end = JustSayItTheme.Spacing.spaceBase),
            visible = (collapse && isSelected),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            ChipSm(
                modifier = Modifier.zIndex(1f),
                moodItem = item,
                isSelected = isSelected,
                onClick = { item: MoodItem ->
                    if (isSelected) onChange(null)
                },
                isActive = !isOwner
            )
        }
    }
}

//@Preview
//@Composable
//fun FeedItemPreview() {
//    val moodItems = rememberMoodItemsForFeed(1, 2, 3, 5)
//
//    var selectedMood by remember {
//        mutableStateOf<MoodItem?>(null)
//    }
//
//    Feed(
//        profileUrl = "https://github.com/kmkim2689/Android-Wiki/assets/101035437/e310b0cf-f931-4afe-98b1-8d7b88900a0f",
//        nickname = "케이엠",
//        date = "2024-01-18",
//        text = "안녕하세요 피드 미리보기 테스트입니다. 안녕하세요 피드 미리보기 테ㅅ트입니다. 안녕하세요 피드 미리보기 테스트입니다.",
//        images = listOf(
//            "https://github.com/kmkim2689/Android-Wiki/assets/101035437/0572b856-8439-43a1-b9f0-79897a29ae60",
//            "https://github.com/kmkim2689/Android-Wiki/assets/101035437/6fbf0375-5299-4d1c-a95e-6a04bad00eac",
//            "https://github.com/kmkim2689/Android-Wiki/assets/101035437/15d4c57c-67d9-4c31-aad0-883d769025ca"
//        ),
//        selectedSympathy = selectedMood,
//        sympathyItems = moodItems,
//        onSympathyItemClick = {
//            selectedMood = it
//        },
//        onMenuClick = {},
//        onFeedClick = {},
//        isOwner = false
//    )
//}