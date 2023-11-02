package com.replace.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiaryDetailResponse(
    @SerialName("id") val id: Long,
    @SerialName("images") val images: List<String>,
    @SerialName("place") val place: PlaceResponse,
    @SerialName("createdAt") val createdAt: String,
    @SerialName("writer") val writer: WriterResponse,
    @SerialName("title") val title: String,
    @SerialName("content") val content: String,
)
