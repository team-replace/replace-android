package com.app.replace.ui.common.mapper

import com.app.replace.ui.model.CoordinateUiModel
import com.replace.data.model.response.CoordinateResponse

fun CoordinateResponse.toUi(): CoordinateUiModel {
    return CoordinateUiModel(latitude = this.latitude, longitude = this.longitude)
}
