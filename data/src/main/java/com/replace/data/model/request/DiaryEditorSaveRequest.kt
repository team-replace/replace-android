package com.replace.data.model.request

import com.replace.data.model.CoordinateDataModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiaryEditorSaveRequest(
    @SerialName("images") val images: List<String>,
    @SerialName("title") val title: String,
    @SerialName("content") val content: String,
    @SerialName("shareScope") val shareScope: String,
    @SerialName("coordinate") val coordinate: CoordinateDataModel,
)
