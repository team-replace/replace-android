package com.replace.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoordinateDataModel(
    @SerialName("latitude") val latitude: String,
    @SerialName("longitude") val longitude: String,
)
