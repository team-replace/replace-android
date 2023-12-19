package com.replace.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiaryCoordinatesResponse(
    @SerialName("diaryCoordinates") val diaryCoordinates: List<CoordinateResponse>,
)
