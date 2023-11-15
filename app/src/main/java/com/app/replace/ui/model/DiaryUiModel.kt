package com.app.replace.ui.model

data class DiaryUiModel(
    val user: WriterUiModel,
    val contents: List<DiaryContentUiModel>,
)
