package com.replace.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiaryResponse(
    @SerialName("user")
    val user: WriterResponse,
    @SerialName("contents")
    val contents: List<DiaryContentResponse>,
)
