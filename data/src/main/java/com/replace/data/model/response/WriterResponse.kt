package com.replace.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WriterResponse(
    @SerialName("profileImage") val profileImage: String,
    @SerialName("nickname") val nickname: String,
)
