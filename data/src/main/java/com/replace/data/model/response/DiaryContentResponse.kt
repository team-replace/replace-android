package com.replace.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiaryContentResponse(
    @SerialName("title") val title: String,
    @SerialName("thumbnails") val thumbnails: List<String>,
    @SerialName("numOfExtraThumbnails") val numOfExtraThumbnails: Int,
    @SerialName("createdAt") val createdAt: String,
)
