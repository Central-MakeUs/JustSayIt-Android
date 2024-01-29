package com.sowhat.report_presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.sowhat.designsystem.R
import com.sowhat.designsystem.common.Mood
import com.sowhat.designsystem.common.noRippleClickable
import com.sowhat.designsystem.component.PopupMenuContents
import com.sowhat.designsystem.component.PopupMenuItem
import com.sowhat.designsystem.component.TimelineFeedImages
import com.sowhat.designsystem.theme.Gray300
import com.sowhat.designsystem.theme.Gray400
import com.sowhat.designsystem.theme.JustSayItTheme

@Composable
fun MyFeed(
    modifier: Modifier = Modifier,
    isOpen: Boolean,
    currentDate: String?,
    mood: Mood?,
    date: String,
    isMoodVisible: Boolean,
    onMenuClick: () -> Unit,
    text: String,
    images: List<String>,
    isScrollInProgress: Boolean,
    sympathyMoodItems: List<@Composable () -> Unit>,
    isEdited: Boolean,
    isMenuVisible: Boolean,
    popupMenuItem: List<PopupMenuItem>,
    onPopupMenuDismiss: () -> Unit,
    onMenuItemClick: (PopupMenuItem) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.Transparent)
    ) {

        if (isMoodVisible && isScrollInProgress) {
            DateBadge(
                modifier = Modifier
                    .padding(top = 48.dp)
                    .zIndex(2f),
                date = date,
                currentDate = currentDate
            )
        }

//        Row(
//            modifier = modifier
//                .fillMaxWidth()
//                .background(color = Color.Transparent),
//            horizontalArrangement = Arrangement
//                .spacedBy(JustSayItTheme.Spacing.spaceXS)
//        ) {
//            MoodStatus(
//                mood = mood,
//                isStatusVisible = isStatusVisible
//            )
//
//        }
        FeedCard(
            modifier = Modifier.padding(start = 36.dp),
            isOpen = isOpen,
            onMenuClick = onMenuClick,
            text = text,
            images = images,
            sympathyMoodItems = sympathyMoodItems,
            isEdited = isEdited,
            isMenuVisible = isMenuVisible,
            popupMenuItem = popupMenuItem,
            onPopupMenuDismiss = onPopupMenuDismiss,
            onMenuItemClick = onMenuItemClick
        )
    }
}

@Composable
private fun FeedCard(
    modifier: Modifier = Modifier,
    isOpen: Boolean,
    onMenuClick: () -> Unit,
    text: String,
    images: List<String>,
    sympathyMoodItems: List<@Composable () -> Unit>,
    isEdited: Boolean,
    isMenuVisible: Boolean,
    popupMenuItem: List<PopupMenuItem>,
    onPopupMenuDismiss: () -> Unit,
    onMenuItemClick: (PopupMenuItem) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = JustSayItTheme.Shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = JustSayItTheme.Colors.mainBackground
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        border = BorderStroke(JustSayItTheme.Spacing.border, Gray300)
    ) {
        FeedContent(
            isOpen = isOpen,
            onMenuClick = onMenuClick,
            text = text,
            images = images,
            sympathyMoodItems = sympathyMoodItems,
            isEdited = isEdited,
            isMenuVisible = isMenuVisible,
            popupMenuItems = popupMenuItem,
            onPopupMenuDismiss = onPopupMenuDismiss,
            onMenuItemClick = onMenuItemClick
        )
    }
}

@Composable
private fun FeedContent(
    isOpen: Boolean,
    onMenuClick: () -> Unit,
    text: String,
    images: List<String>,
    sympathyMoodItems: List<@Composable () -> Unit>,
    isEdited: Boolean,
    modifier: Modifier = Modifier,
    isMenuVisible: Boolean,
    popupMenuItems: List<PopupMenuItem>,
    onPopupMenuDismiss: () -> Unit,
    onMenuItemClick: (PopupMenuItem) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = JustSayItTheme.Spacing.spaceBase),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = JustSayItTheme.Spacing.spaceBase),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MyFeedMetaData(isOpen = isOpen, isEdited = isEdited)

            MenuButton(
                modifier = Modifier,
                isVisible = isMenuVisible,
                items = popupMenuItems,
                onDismiss = onPopupMenuDismiss,
                onMenuClick = onMenuClick,
                onItemClick = onMenuItemClick
            )
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = JustSayItTheme.Spacing.spaceBase,
                    end = JustSayItTheme.Spacing.spaceBase,
                ),
            text = text,
            style = JustSayItTheme.Typography.body1,
            color = JustSayItTheme.Colors.mainTypo
        )

        FeedImages(images)

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = JustSayItTheme.Spacing.spaceBase),
            reverseLayout = true,
        ) {
            val reversedItems = sympathyMoodItems.asReversed()

            item {
                Spacer(modifier = Modifier.width(JustSayItTheme.Spacing.spaceBase))
            }

            itemsIndexed(reversedItems) { index, item ->
                item()
                Spacer(modifier = Modifier.width(JustSayItTheme.Spacing.spaceXS))
            }

        }
    }
}

@Composable
private fun FeedImages(images: List<String>) {
    if (images.isNotEmpty()) {
        TimelineFeedImages(
            modifier = Modifier.padding(
                start = JustSayItTheme.Spacing.spaceBase,
                end = JustSayItTheme.Spacing.spaceBase,
                top = JustSayItTheme.Spacing.spaceBase,
            ),
            models = images
        )
    }
}

@Composable
private fun MenuButton(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    items: List<PopupMenuItem>,
    onDismiss: () -> Unit,
    onItemClick: (PopupMenuItem) -> Unit,
    onMenuClick: () -> Unit
) {
    Box(
        modifier = modifier
            .noRippleClickable { onMenuClick() }
    ) {
        Icon(
            modifier = Modifier.padding(JustSayItTheme.Spacing.spaceBase),
            painter = painterResource(id = R.drawable.ic_more_20),
            contentDescription = "more",
            tint = JustSayItTheme.Colors.mainTypo
        )

        // Box를 하나 더 두는 이유는
        // 해당 박스의 우측 하단에 메뉴가 들어가게 되는 특성을 활용해(팝업메뉴가 박스 크기를 초과하기 때문)
        // 팝업 메뉴의 위치를 조절하기 위함
        Box(modifier = Modifier.size(JustSayItTheme.Spacing.spaceXXL)) {
            MyFeedPopupMenu(
                isVisible = isVisible,
                items = items,
                onDismiss = onDismiss,
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
private fun MyFeedPopupMenu(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    items: List<PopupMenuItem>,
    onDismiss: () -> Unit,
    onItemClick: (PopupMenuItem) -> Unit
) {
    PopupMenuContents(
        modifier = modifier,
        isVisible = isVisible,
        items = items,
        onDismiss = onDismiss,
        onItemClick = onItemClick
    )
}

@Composable
private fun MyFeedMetaData(modifier: Modifier = Modifier, isOpen: Boolean, isEdited: Boolean) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(JustSayItTheme.Spacing.spaceXS),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OpenStatusBadge(isOpen = isOpen)

        if (isEdited) Text(
            text = stringResource(id = R.string.report_edited),
            style = JustSayItTheme.Typography.detail1,
            color = Gray400
        )
    }
}

@Composable
fun MoodStatus(
    modifier: Modifier = Modifier,
    mood: Mood?,
    isStatusVisible: Boolean
) {
    Box(
        modifier = modifier
            .width(24.dp)
            .padding(top = JustSayItTheme.Spacing.spaceBase),
        contentAlignment = Alignment.Center
    ) {
        if (isStatusVisible && mood != null) {
            Image(
                modifier = Modifier
                    .size(24.dp)
                    .aspectRatio(1f),
                painter = painterResource(id = mood.drawable!!),
                contentDescription = "mood_icon"
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun MyFeedPreview() {
    LazyColumn() {
        item {
            MyFeed(
                isOpen = true,
                mood = Mood.HAPPY,
                isMoodVisible = true,
                text = "ok\nok",
                images = emptyList(),
                onMenuClick = {},
                date = "22.11.22",
                isScrollInProgress = true,
                currentDate = "22.11.22",
                sympathyMoodItems = emptyList(),
                isEdited = true,
                isMenuVisible = false,
                popupMenuItem = emptyList(),
                onPopupMenuDismiss = {},
                onMenuItemClick = {}
            )
            
            Spacer(modifier = Modifier.height(JustSayItTheme.Spacing.spaceBase))
        }

        item {
            MyFeed(
                isOpen = true,
                mood = Mood.HAPPY,
                isMoodVisible = true,
                text = "ok\nok",
                images = listOf("", "", ""),
                onMenuClick = {},
                date = "22.11.23",
                isScrollInProgress = true,
                currentDate = "22.11.23",
                sympathyMoodItems = emptyList(),
                isEdited = false,
                isMenuVisible = false,
                popupMenuItem = emptyList(),
                onPopupMenuDismiss = {},
                onMenuItemClick = {}
            )
        }
    }
}