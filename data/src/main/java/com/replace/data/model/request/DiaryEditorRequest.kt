package com.replace.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiaryEditorRequest(
    @SerialName("images") val images: List<String>,
    @SerialName("title") val title: String,
    @SerialName("content") val content: String,
    @SerialName("shareScope") val shareScope: String,
)
