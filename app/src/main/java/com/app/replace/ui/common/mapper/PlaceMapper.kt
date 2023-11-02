package com.app.replace.ui.common.mapper

import com.app.replace.ui.model.PlaceUiModel
import com.replace.data.model.response.PlaceResponse

fun PlaceResponse.toUi(): PlaceUiModel {
    return PlaceUiModel(
        spotName = this.spotName,
        roadAddress = this.roadAddress,
    )
}
