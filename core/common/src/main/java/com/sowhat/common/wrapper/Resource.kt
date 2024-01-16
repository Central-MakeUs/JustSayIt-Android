package com.sowhat.common.wrapper

sealed class Resource<T: Any?>(
    val data: T? = null,
    val code: String? = null,
    val message: String? = null
) {
    class Success<T>(data: T, code: String, message: String) : Resource<T>(data, code, message)
    class Error<T>(data: T? = null, code: String? = null, message: String) : Resource<T>(data, code, message)
    class Loading<T> : Resource<T>()
}
