package com.app.replace.ui.common.mapper

import com.app.replace.ui.model.PlaceDiaryUiModel
import com.app.replace.ui.model.PlaceInfoUiModel
import com.app.replace.ui.model.PlaceUiModel
import com.replace.data.model.response.PlaceDiaryResponse
import com.replace.data.model.response.PlaceInfoResponse
import com.replace.data.model.response.PlaceResponse

fun PlaceInfoResponse.toUi(): PlaceInfoUiModel {
    return PlaceInfoUiModel(
        place = this.place.toUi(),
        coupleDiaries = this.coupleDiaries.map { it.toUi() },
        allDiaries = this.allDiaries.map { it.toUi() },
    )
}

fun PlaceDiaryResponse.toUi(): PlaceDiaryUiModel {
    return PlaceDiaryUiModel(
        id = this.id,
        user = this.user.toUi(),
        title = this.title,
        thumbnails = this.thumbnails,
        numOfExtraThumbnails = this.numOfExtraThumbnails,
        createdAt = this.createdAt,
    )
}

fun PlaceResponse.toUi(): PlaceUiModel {
    return PlaceUiModel(
        spotName = this.spotName,
        roadAddress = this.roadAddress,
    )
}
