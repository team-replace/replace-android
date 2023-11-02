package com.replace.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaceResponse(
    @SerialName("spotName") val spotName: String,
    @SerialName("roadAddress") val roadAddress: String,
)
