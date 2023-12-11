package com.replace.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConnectionCodeResponse(
    @SerialName("code") val code: String,
)
