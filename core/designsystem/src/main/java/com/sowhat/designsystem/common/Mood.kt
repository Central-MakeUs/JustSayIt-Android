package com.sowhat.designsystem.common

import android.os.Parcelable
import com.sowhat.designsystem.R
import kotlinx.parcelize.Parcelize

const val MOOD_HAPPY = "EMOTION001"
const val MOOD_SAD = "EMOTION002"
const val MOOD_SURPRISED = "EMOTION003"
const val MOOD_ANGRY = "EMOTION004"

const val POST_HAPPY = "MOOD001"
const val POST_SAD = "MOOD002"
const val POST_SURPRISED = "MOOD003"
const val POST_ANGRY = "MOOD004"

@Parcelize
enum class Mood(
    val title: String,
    val postData: String?,
    val drawable: Int?,
    val moodCode: String?
) : Parcelable {
    ALL("전체", null, null, null),
    HAPPY("행복", MOOD_HAPPY, R.drawable.ic_happy_96, POST_HAPPY),
    SAD("슬픔", MOOD_SAD, R.drawable.ic_sad_96, POST_SAD),
    SURPRISED("놀람", MOOD_SURPRISED, R.drawable.ic_surprise_96, POST_SURPRISED),
    ANGRY("화남", MOOD_ANGRY, R.drawable.ic_angry_96, POST_ANGRY);

    companion object {
        fun getMoodDataByTitle(queryTitle: String): Mood? {
            return Mood.values().find { it.title == queryTitle }
        }
    }
}