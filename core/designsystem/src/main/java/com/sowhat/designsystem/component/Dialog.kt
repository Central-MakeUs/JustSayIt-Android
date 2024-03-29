package com.sowhat.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.sowhat.designsystem.R
import com.sowhat.designsystem.common.rippleClickable
import com.sowhat.designsystem.theme.Black
import com.sowhat.designsystem.theme.Gray500
import com.sowhat.designsystem.theme.JustSayItTheme
import com.sowhat.designsystem.theme.White

@Composable
fun DialogCard(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    Dialog(
        properties = DialogProperties(),
        onDismissRequest = onDismiss
    ) {
        Card(
            modifier = modifier,
            shape = JustSayItTheme.Shapes.medium,
            colors = CardDefaults.cardColors(
                containerColor = JustSayItTheme.Colors.mainBackground
            )
        ) {
            content()
        }
    }
}

@Composable
fun ImageDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    imageUrl: Any?
) {
    Dialog(
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        ),
        onDismissRequest = onDismiss
    ) {

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(Black),
            contentAlignment = Alignment.Center
        ) {
            // 1을 빼는 이유는 1이 초기값이기 때문 -> 초기에는 extraWidth/Height가 0이어야 할 것
            // 즉 줌이 다 빠진 시점에는 panning을 해도 사진이 경계 안으로 들어가지 않도록 하기 위함
            var scale by remember { mutableStateOf(1f) }
            var offset by remember { mutableStateOf(Offset.Zero) }
            val minScale = 1f
            val maxScale = 3f

            val state = rememberTransformableState { zoomChange, panChange, rotationChange ->
                scale = (scale * zoomChange).coerceIn(minScale, maxScale)
                val extraWidth = (scale - 1) * constraints.maxWidth
                val extraHeight = (scale - 1) * constraints.maxHeight
                val maxX = extraWidth / 2
                val maxY = extraHeight / 2
                offset = Offset(
                    x = (offset.x + panChange.x).coerceIn(-maxX, maxX),
                    y = (offset.y + panChange.y).coerceIn(-maxY, maxY)
                )
            }

            AsyncImage(
                modifier = modifier
                    .fillMaxWidth()
                    .graphicsLayer(
                        scaleX = scale,
                        scaleY = scale,
                        translationX = offset.x,
                        translationY = offset.y,
                    )
                    .transformable(state)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onDoubleTap = {
                                scale = when {
                                    scale > 2f -> 1f
                                    else -> 3f
                                }
                            }
                        )
                    },
                model = imageUrl,
                contentDescription = "image"
            )
        }

        Box(
            modifier = Modifier.padding(JustSayItTheme.Spacing.spaceXS),
            contentAlignment = Alignment.TopStart
        ) {
            IconButton(
                onClick = onDismiss
            ) {
                Icon(
                    modifier = Modifier.size(JustSayItTheme.Spacing.spaceXL),
                    painter = painterResource(id = R.drawable.ic_close_default_20),
                    contentDescription = "close",
                    tint = White
                )
            }
        }
    }
}

@Composable
fun AlertDialog(
    modifier: Modifier = Modifier,
    title: String,
    subTitle: String,
    buttonContent: Pair<String, String>,
    onAccept: () -> Unit,
    onDismiss: () -> Unit,
) {

    DialogCard(
        onDismiss = onDismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(JustSayItTheme.Spacing.spaceMd),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            DialogText(
                title = title,
                subTitle = subTitle
            )

            DialogButtons(
                buttonContent = buttonContent,
                onDismiss = onDismiss,
                onAccept = onAccept
            )
        }
    }
}

@Composable
fun AlertDialog(
    modifier: Modifier = Modifier,
    targetId: Long,
    title: String,
    subTitle: String,
    buttonContent: Pair<String, String>,
    onAccept: (Long) -> Unit,
    onDismiss: () -> Unit,
) {

    DialogCard(
        onDismiss = onDismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(JustSayItTheme.Spacing.spaceMd),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            DialogText(
                title = title,
                subTitle = subTitle
            )

            DialogButtons(
                buttonContent = buttonContent,
                onDismiss = onDismiss,
                onAccept = {
                    onAccept(targetId)
                }
            )
        }
    }
}

@Composable
fun AlertDialogReverse(
    modifier: Modifier = Modifier,
    title: String,
    subTitle: String,
    buttonContent: Pair<String, String>,
    onAccept: () -> Unit,
    onDismiss: () -> Unit,
) {
    DialogCard(
        onDismiss = onDismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(JustSayItTheme.Spacing.spaceMd),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            DialogText(
                title = title,
                subTitle = subTitle
            )

            DialogButtons(
                buttonContent = buttonContent,
                onDismiss = onDismiss,
                onAccept = onAccept,
                dismissButtonColor = JustSayItTheme.Colors.subBackground,
                dismissTextColor = Gray500,
                acceptButtonColor = JustSayItTheme.Colors.mainTypo,
                acceptTextColor = JustSayItTheme.Colors.mainBackground
            )
        }

    }
}

@Composable
fun AlertDialogReverse(
    modifier: Modifier = Modifier,
    title: String,
    targetId: Long,
    subTitle: String,
    buttonContent: Pair<String, String>,
    onAccept: (Long) -> Unit,
    onDismiss: () -> Unit,
) {
    DialogCard(
        onDismiss = onDismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(JustSayItTheme.Spacing.spaceMd),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            DialogText(
                title = title,
                subTitle = subTitle
            )

            DialogButtons(
                buttonContent = buttonContent,
                onDismiss = onDismiss,
                onAccept = { onAccept(targetId) },
                dismissButtonColor = JustSayItTheme.Colors.subBackground,
                dismissTextColor = Gray500,
                acceptButtonColor = JustSayItTheme.Colors.mainTypo,
                acceptTextColor = White
            )
        }

    }
}

@Composable
fun SelectionAlertDialog(
    modifier: Modifier = Modifier,
    title: String,
    subTitle: String,
    selectedItem: String?,
    selectionItems: List<String>,
    buttonText: String,
    onChange: (String) -> Unit,
    onAccept: () -> Unit,
    onDismiss: () -> Unit,
) {
    DialogCard(
        modifier = modifier,
        onDismiss = onDismiss
    ) {
        Box(
            contentAlignment = Alignment.TopEnd
        ) {
            DefaultIconButton(
                iconDrawable = R.drawable.ic_close_default_20,
                onClick = onDismiss
            )

            SelectionDialogContent(
                title = title,
                subTitle = subTitle,
                selectionItems = selectionItems,
                selectedItem = selectedItem,
                onChange = onChange,
                buttonText = buttonText,
                onAccept = onAccept,
                onDismiss = onDismiss
            )
        }
    }
}

@Composable
private fun SelectionDialogContent(
    title: String,
    subTitle: String,
    selectionItems: List<String>,
    selectedItem: String?,
    onChange: (String) -> Unit,
    buttonText: String,
    onAccept: () -> Unit,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(JustSayItTheme.Spacing.spaceMd),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(28.dp)
    ) {
        DialogText(
            title = title,
            subTitle = subTitle
        )

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            selectionItems.forEach {
                SelectionButton(
                    text = it,
                    isSelected = it == selectedItem,
                    onClick = onChange
                )
            }
        }

        DefaultButtonFull(
            modifier = Modifier,
            text = buttonText,
            textStyle = JustSayItTheme.Typography.body1,
            onClick = onAccept,
            isActive = selectedItem != null
        )
    }
}

@Composable
fun SelectionButton(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    onClick: (String) -> Unit
) {

    val textStyle = if (isSelected) JustSayItTheme.Typography.body4 else JustSayItTheme.Typography.body3

    Box(
        modifier = modifier
            .fillMaxWidth()
            .rippleClickable { onClick(text) }
    ) {
        Text(
            modifier = Modifier.padding(
                horizontal = JustSayItTheme.Spacing.spaceBase,
                vertical = 10.dp
            ),
            text = text,
            style = textStyle,
            color = JustSayItTheme.Colors.mainTypo
        )
    }
}

@Composable
private fun DialogButtons(
    buttonContent: Pair<String, String>,
    onDismiss: () -> Unit,
    onAccept: () -> Unit,
    dismissButtonColor: Color = JustSayItTheme.Colors.mainTypo,
    dismissTextColor: Color = JustSayItTheme.Colors.mainBackground,
    acceptButtonColor: Color = JustSayItTheme.Colors.subBackground,
    acceptTextColor: Color = Gray500
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement
            .spacedBy(JustSayItTheme.Spacing.spaceXS)
    ) {
        DefaultButtonFull(
            modifier = Modifier.weight(1f),
            text = buttonContent.first,
            textStyle = JustSayItTheme.Typography.body1,
            textColor = dismissTextColor,
            backgroundColor = dismissButtonColor,
            onClick = onDismiss
        )

        DefaultButtonFull(
            modifier = Modifier.weight(1f),
            text = buttonContent.second,
            textStyle = JustSayItTheme.Typography.body1,
            textColor = acceptTextColor,
            backgroundColor = acceptButtonColor,
            onClick = onAccept
        )
    }
}

@Composable
private fun DialogText(title: String, subTitle: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(JustSayItTheme.Spacing.spaceBase)
    ) {
        Text(
            text = title,
            style = JustSayItTheme.Typography.body2,
            color = JustSayItTheme.Colors.mainTypo,
            textAlign = TextAlign.Center
        )

        Text(
            text = subTitle,
            style = JustSayItTheme.Typography.detail1,
            color = JustSayItTheme.Colors.subTypo,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun DialogPreview() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AlertDialog(
            title = "작성중인 글이 있어요",
            subTitle = "그만 작성하기를 누르면\n작성중인 글은 저장되지 않아요.",
            buttonContent = "그만 작성하기" to "계속 작성하기",
            onAccept = { /*TODO*/ }) {

        }
    }
}

@Preview
@Composable
fun SelectionDialogPreview() {
    val items = listOf("광고/도배글이에요.", "음란물이에요.", "도박이에요.")
    var selected by remember {
        mutableStateOf<String?>(null)
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SelectionAlertDialog(
            title = "게시글 신고하기",
            subTitle = "게시글을 신고하는 이유가 무엇인가요?",
            selectedItem = selected,
            selectionItems = items,
            buttonText = "신고하기",
            onChange = { selected = it },
            onAccept = { /*TODO*/ },
            onDismiss = {}
        )
    }
}