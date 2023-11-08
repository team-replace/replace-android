package com.replace.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConnectionCodeRequest(
    @SerialName("code") val code: String,
)
