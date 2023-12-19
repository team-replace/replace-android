package com.app.replace.ui.common.mapper

import com.app.replace.ui.model.CoordinateUiModel
import com.replace.data.model.CoordinateDataModel
import com.replace.data.model.response.CoordinateResponse

fun CoordinateResponse.toUi(): CoordinateUiModel {
    return CoordinateUiModel(latitude = this.latitude, longitude = this.longitude)
}

fun CoordinateDataModel.toUi(): CoordinateUiModel {
    return CoordinateUiModel(latitude = this.latitude, longitude = this.longitude)
}

fun CoordinateUiModel.toData(): CoordinateDataModel {
    return CoordinateDataModel(latitude = this.latitude, longitude = this.longitude)
}
