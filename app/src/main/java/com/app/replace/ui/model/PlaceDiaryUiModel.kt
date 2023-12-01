package com.app.replace.ui.model

data class PlaceDiaryUiModel(
    val id: Long,
    val user: WriterUiModel,
    val title: String,
    val thumbnails: List<String>,
    val numOfExtraThumbnails: Int,
    val createdAt: String,
)
