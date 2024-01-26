package com.sowhat.designsystem.component

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.viewinterop.AndroidView

// https://medium.com/make-apps-simple/emojis-in-jetpack-compose-66649b9ad6c2
@Composable
fun EmojiView(
    emoji: String,
    size: Float
) {
    AndroidView(
        factory = { context ->
            AppCompatTextView(context).apply {
                setTextColor(Black.toArgb())
                text = emoji ?: "😟"
                textSize = size
                textAlignment = View.TEXT_ALIGNMENT_CENTER
            }
        },
        update = {
            it.apply {
                text = emoji ?: "😟"
            }
        }
    )
}

@Composable
fun HappyEmoji(
    size: Float
) {
    EmojiView(emoji = "\uD83D\uDE03", size = size)
}

@Composable
fun SurprisedEmoji(
    size: Float
) {
    EmojiView(emoji = "\uD83D\uDE32", size = size)
}

@Composable
fun SadEmoji(
    size: Float
) {
    EmojiView(emoji = "\uD83E\uDD7A", size = size)
}

@Composable
fun AngryEmoji(
    size: Float
) {
    EmojiView(emoji = "\uD83D\uDE21", size = size)
}

@Preview
@Composable
fun EmojiPreview() {
    Column {
        HappyEmoji(size = 72f)
        SadEmoji(size = 72f)
        AngryEmoji(size = 72f)
        SurprisedEmoji(size = 72f)
    }
}