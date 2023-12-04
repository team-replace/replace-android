package com.replace.data.model.response

import kotlinx.serialization.SerialName

data class DiaryCoordinatesResponse(
    @SerialName("diaryCoordinates") val diaryCoordinates: List<CoordinateResponse>,
)
