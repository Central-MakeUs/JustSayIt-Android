package com.sowhat.justsayitt

import com.sowhat.justsayitt.AppFirebaseMessagingService.Companion.EVENT_CHANNEL_ID
import com.sowhat.justsayitt.AppFirebaseMessagingService.Companion.EVENT_TITLE

enum class NotificationChannels(val channelId: String, val title: String) {
    EVENT(EVENT_CHANNEL_ID, EVENT_TITLE),
//    ETC(ETC_CHANNEL_ID)
}
