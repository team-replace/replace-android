package com.replace.data.common

data class CustomThrowable(
    val code: Int,
    override val message: String,
) : Throwable(message)
