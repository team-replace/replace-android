package com.replace.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponse(
    @SerialName("user") val user: WriterResponse,
    @SerialName("partner") val partner: WriterResponse? = null,
)
