package com.app.replace.ui.model

data class PlaceInfoUiModel(
    val place: PlaceUiModel,
    val coupleDiaries: List<PlaceDiaryUiModel>,
    val allDiaries: List<PlaceDiaryUiModel>,
)
