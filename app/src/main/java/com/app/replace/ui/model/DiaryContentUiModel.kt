package com.app.replace.ui.model

data class DiaryContentUiModel(
    val id: Long,
    val title: String,
    val thumbnails: List<String>,
    val numOfExtraThumbnails: Int,
    val createdAt: String,
)
