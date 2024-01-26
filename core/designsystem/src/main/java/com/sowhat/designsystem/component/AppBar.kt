package com.sowhat.designsystem.component

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.TopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sowhat.designsystem.R
import com.sowhat.designsystem.common.ActionButtonItem
import com.sowhat.designsystem.common.DropdownItem
import com.sowhat.designsystem.common.Emotion
import com.sowhat.designsystem.common.bottomBorder
import com.sowhat.designsystem.theme.Gray500
import com.sowhat.designsystem.theme.JustSayItTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    title: String?,
    navigationIcon: Int?,
    actionIcon: Int?,
    onNavigationIconClick: () -> Unit = {},
    onActionIconClick: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        modifier = modifier
            .height(48.dp)
            .bottomBorder(strokeWidth = 1.dp, color = JustSayItTheme.Colors.subSurface),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = JustSayItTheme.Colors.mainSurface,
        ),
        title = { 
            title?.let {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = title,
                        style = JustSayItTheme.Typography.body1,
                        color = JustSayItTheme.Colors.mainTypo
                    )
                }
            }
        },
        navigationIcon = {
            navigationIcon?.let {
                DefaultIconButton(
                    iconDrawable = navigationIcon,
                    onClick = onNavigationIconClick
                )
            }
        },
        actions = {
            actionIcon?.let {
                DefaultIconButton(
                    iconDrawable = actionIcon,
                    onClick = onActionIconClick
                )
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    title: String?,
    textStyle: TextStyle = JustSayItTheme.Typography.body1,
    navigationIcon: Int?,
    actionText: String?,
    onNavigationIconClick: () -> Unit = {},
    onActionTextClick: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        modifier = modifier
            .height(48.dp)
            .bottomBorder(strokeWidth = 1.dp, color = JustSayItTheme.Colors.subSurface),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = JustSayItTheme.Colors.mainSurface,
        ),
        title = {
            title?.let {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = title,
                        style = JustSayItTheme.Typography.body1,
                        color = JustSayItTheme.Colors.mainTypo
                    )
                }
            }
        },
        navigationIcon = {
            navigationIcon?.let {
                DefaultIconButton(
                    iconDrawable = navigationIcon,
                    onClick = onNavigationIconClick
                )
            }
        },
        actions = {
            actionText?.let {
                DefaultTextButton(
                    text = actionText,
                    onClick = onActionTextClick,
                    textStyle = textStyle
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    title: String?,
    textStyle: TextStyle = JustSayItTheme.Typography.body1,
    navigationIcon: Int?,
    actionText: String?,
    isValid: Boolean,
    onNavigationIconClick: () -> Unit = {},
    onActionTextClick: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        modifier = modifier
            .height(48.dp)
            .bottomBorder(strokeWidth = 1.dp, color = JustSayItTheme.Colors.subSurface),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = JustSayItTheme.Colors.mainSurface,
        ),
        title = {
            title?.let {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = title,
                        style = JustSayItTheme.Typography.body1,
                        color = JustSayItTheme.Colors.mainTypo
                    )
                }
            }
        },
        navigationIcon = {
            navigationIcon?.let {
                DefaultIconButton(
                    iconDrawable = navigationIcon,
                    onClick = onNavigationIconClick
                )
            }
        },
        actions = {
            val textColor = if (isValid) textStyle.color else Gray500
            actionText?.let {
                DefaultTextButton(
                    text = actionText,
                    onClick = onActionTextClick,
                    textStyle = textStyle,
                    textColor = textColor,
                    enabled = isValid
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarHome(
    modifier: Modifier = Modifier,
    title: String?,
    actions: List<ActionButtonItem>
) {
    CenterAlignedTopAppBar(
        modifier = modifier
            .height(48.dp)
            .bottomBorder(strokeWidth = 1.dp, color = JustSayItTheme.Colors.subSurface),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = JustSayItTheme.Colors.mainSurface,
        ),
        title = {},
        navigationIcon = {
            title?.let {
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = JustSayItTheme.Spacing.spaceSm),
                        text = title,
                        style = JustSayItTheme.Typography.headlineB,
                        textAlign = TextAlign.Center,
                        color = JustSayItTheme.Colors.mainTypo
                    )
                }
            }
        },
        actions = {
            actions.forEach {
                DefaultIconButton(
                    iconDrawable = it.icon,
                    onClick = it.onClick
                )
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarHome(
    modifier: Modifier = Modifier,
    currentEmotion: Emotion,
    onClick: (String) -> Unit
) {
    val emotions = Emotion.values().toList()

    CenterAlignedTopAppBar(
        modifier = modifier
            .height(48.dp)
            .bottomBorder(strokeWidth = 1.dp, color = JustSayItTheme.Colors.subSurface),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = JustSayItTheme.Colors.mainSurface,
        ),
        title = {
            EmotionButtons(emotions, currentEmotion, onClick)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarFeed(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    currentDropdownItem: DropdownItem,
    dropdownItems: List<DropdownItem>,
    isDropdownExpanded: Boolean,
    onDropdownHeaderClick: (Boolean) -> Unit,
    onDropdownMenuChange: (DropdownItem) -> Unit,
    tabItems: List<String>,
    selectedTabItem: String,
    selectedTabItemColor: Color,
    unselectedTabItemColor: Color,
    onSelectedTabItemChange: (String) -> Unit,
) {

    Box(
        modifier
            .fillMaxWidth()
            .height(48.dp),
//        .bottomBorder(
//            strokeWidth = 1.dp,
//            color = Gray500
//        ),
        contentAlignment = Alignment.BottomCenter
    ) {
        CenterAlignedTopAppBar(
            modifier = Modifier,
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = JustSayItTheme.Colors.mainSurface,
            ),
            title = {},
            navigationIcon = {
                DropdownHeader(
                    currentMenu = currentDropdownItem,
                    isDropdownExpanded = isDropdownExpanded,
                    onClick = onDropdownHeaderClick
                )

                DropdownContents(
                    modifier = Modifier.width(92.dp),
                    isVisible = isDropdownExpanded,
                    items = dropdownItems,
                    onDismiss = { onDropdownHeaderClick(!isDropdownExpanded) },
                    onItemClick = onDropdownMenuChange
                )
            },
            actions = {
                Tab(
                    selectedItem = selectedTabItem,
                    items = tabItems,
                    selectedColor = selectedTabItemColor,
                    unselectedColor = unselectedTabItemColor,
                    onSelectedItemChange = onSelectedTabItemChange
                )
            },
        )

        Divider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = JustSayItTheme.Colors.subBackground
        )
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarMyPage(
    modifier: Modifier = Modifier,
    currentDropdownItem: DropdownItem,
    dropdownItems: List<DropdownItem>,
    isDropdownExpanded: Boolean,
    onDropdownHeaderClick: (Boolean) -> Unit,
    onDropdownMenuChange: (DropdownItem) -> Unit,
    tabItems: List<String>,
    selectedTabItem: String,
    selectedTabItemColor: Color,
    unselectedTabItemColor: Color,
    onSelectedTabItemChange: (String) -> Unit,
) {

    Box(
        modifier
            .fillMaxWidth()
            .height(48.dp),
//        .bottomBorder(
//            strokeWidth = 1.dp,
//            color = Gray500
//        ),
        contentAlignment = Alignment.BottomCenter
    ) {
        CenterAlignedTopAppBar(
            modifier = Modifier,
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = JustSayItTheme.Colors.mainSurface,
            ),
            title = {},
            navigationIcon = {
                DropdownHeader(
                    currentMenu = currentDropdownItem,
                    isDropdownExpanded = isDropdownExpanded,
                    onClick = onDropdownHeaderClick
                )

                DropdownContents(
                    modifier = Modifier.width(92.dp),
                    isVisible = isDropdownExpanded,
                    items = dropdownItems,
                    onDismiss = { onDropdownHeaderClick(!isDropdownExpanded) },
                    onItemClick = onDropdownMenuChange
                )
            },
            actions = {
                Tab(
                    selectedItem = selectedTabItem,
                    items = tabItems,
                    selectedColor = selectedTabItemColor,
                    unselectedColor = unselectedTabItemColor,
                    onSelectedItemChange = onSelectedTabItemChange
                )
            },
        )

        Divider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = JustSayItTheme.Colors.subBackground
        )
    }


}

@Composable
private fun EmotionButtons(
    emotions: List<Emotion>,
    currentEmotion: Emotion,
    onClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = JustSayItTheme.Spacing.spaceXXL,
                vertical = JustSayItTheme.Spacing.spaceXS
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        emotions.forEachIndexed { index, emotion ->
            EmotionButton(
                modifier = Modifier,
                emotion = emotion,
                currentEmotion = currentEmotion,
                onClick = onClick
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
class ScrollBehavior: TopAppBarScrollBehavior {
    override val flingAnimationSpec: DecayAnimationSpec<Float> = TODO()
    override val isPinned: Boolean = TODO()
    override val nestedScrollConnection: NestedScrollConnection = TODO()
    override val snapAnimationSpec: AnimationSpec<Float> = TODO()
    override val state: TopAppBarState = TODO()
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun AppBarPreview() {

    var isExpanded by remember {
        mutableStateOf(false)
    }

    val dropdownItems = listOf(
        DropdownItem("전체", null),
        DropdownItem("행복", R.drawable.ic_happy_96),
        DropdownItem("슬픔", R.drawable.ic_sad_96),
        DropdownItem("놀람", R.drawable.ic_surprise_96),
        DropdownItem("화남", R.drawable.ic_angry_96),
    )

    val tabItems = listOf("최근글", "인기글")

    var currentItem by remember {
        mutableStateOf(dropdownItems.first())
    }
    
    var currentTabItem by remember {
        mutableStateOf(tabItems.first())
    }

    Column {
        AppBar(title = "설정", navigationIcon = R.drawable.ic_back_24, actionIcon = R.drawable.ic_menu_24)
        Spacer(modifier = Modifier.height(2.dp))
        AppBar(title = "앱바 미리보기", navigationIcon = R.drawable.ic_back_24, actionText = "완료")
        Spacer(modifier = Modifier.height(2.dp))
        AppBar(title = "앱바 미리보기", navigationIcon = null, actionText = null)
        Spacer(modifier = Modifier.height(2.dp))
        AppBarHome(onClick = {}, currentEmotion = Emotion.HAPPY)
        Spacer(modifier = Modifier.height(2.dp))
        AppBarHome(title = "그냥, 그렇다고", actions = listOf(
            ActionButtonItem(icon = R.drawable.ic_camera_24, onClick = {}),
            ActionButtonItem(icon = R.drawable.ic_menu_24, onClick = {})
        ))
        Spacer(modifier = Modifier.height(2.dp))
        AppBarFeed(
            currentDropdownItem = currentItem,
            dropdownItems = dropdownItems,
            isDropdownExpanded = isExpanded,
            onDropdownHeaderClick = { expanded -> isExpanded = expanded },
            onDropdownMenuChange = { dropdownItem ->
                currentItem = dropdownItem
            },
            tabItems = tabItems,
            selectedTabItem = currentTabItem,
            selectedTabItemColor = JustSayItTheme.Colors.mainTypo,
            unselectedTabItemColor = Gray500,
            onSelectedTabItemChange = { tabItem -> currentTabItem = tabItem },
            lazyListState = rememberLazyListState()
        )
    }
}