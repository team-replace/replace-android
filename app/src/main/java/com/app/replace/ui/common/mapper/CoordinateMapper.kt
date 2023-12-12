package com.app.replace.ui.common.mapper

import com.app.replace.ui.model.CoordinateUiModel
import com.replace.data.model.CoordinateDataModel

fun CoordinateDataModel.toUi(): CoordinateUiModel {
    return CoordinateUiModel(latitude = this.latitude, longitude = this.longitude)
}

fun CoordinateUiModel.toData(): CoordinateDataModel {
    return CoordinateDataModel(latitude = this.latitude, longitude = this.longitude)
}
