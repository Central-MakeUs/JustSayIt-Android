package com.sowhat.designsystem.common

import com.sowhat.designsystem.R

const val MOOD_HAPPY = "EMOTION001"
const val MOOD_SAD = "EMOTION002"
const val MOOD_SURPRISED = "EMOTION003"
const val MOOD_ANGRY = "EMOTION004"

enum class Mood(
    val title: String,
    val postData: String?,
    val drawable: Int?
) {
    ALL("전체", null, null),
    HAPPY("행복", MOOD_HAPPY, R.drawable.ic_happy_96),
    SAD("슬픔", MOOD_SAD, R.drawable.ic_sad_96),
    SURPRISED("놀람", MOOD_SURPRISED, R.drawable.ic_surprise_96),
    ANGRY("화남", MOOD_ANGRY, R.drawable.ic_angry_96);

    companion object {
        fun getMoodDataByTitle(queryTitle: String): Mood? {
            return Mood.values().find { it.title == queryTitle }
        }
    }
}