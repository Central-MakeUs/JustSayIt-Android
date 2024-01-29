package com.sowhat.report_presentation.common

import android.util.Log

fun String.toDate(): String = try {
    this.split("T")
        .first()
        .split("-")
        .mapIndexed { index, date -> if (index == 0) date.slice(IntRange(2, 3)) else date }
        .joinToString(".")
} catch (e: Exception) {
    Log.e("toDate", "error : ${e.message}")
    "server error"
}
