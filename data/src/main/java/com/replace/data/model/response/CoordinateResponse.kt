package com.replace.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoordinateResponse(
    @SerialName("latitude") val latitude: String,
    @SerialName("longitude") val longitude: String,
)
