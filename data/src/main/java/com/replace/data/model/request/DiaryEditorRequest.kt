package com.replace.data.model.request

data class DiaryEditorRequest(
    val images: List<String>,
    val title: String,
    val content: String,
    val shareScope: String,
)
