package com.replace.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiaryEditorImageResponse(
    @SerialName("imageUrls") val imageUrls: List<String>,
)
