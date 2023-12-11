package com.replace.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaceInfoResponse(
    @SerialName("place") val place: PlaceResponse,
    @SerialName("coupleDiaries") val coupleDiaries: List<PlaceDiaryResponse>,
    @SerialName("allDiaries") val allDiaries: List<PlaceDiaryResponse>,
)
