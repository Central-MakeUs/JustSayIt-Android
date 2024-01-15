package com.sowhat.network.model

data class ResponseBody<T: Any?>(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: T?
)