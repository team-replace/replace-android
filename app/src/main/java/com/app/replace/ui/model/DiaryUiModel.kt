package com.app.replace.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DiaryUiModel(
    val id: Long,
    val images: List<String>,
    val place: PlaceUiModel,
    val createdAt: String,
    val writer: WriterUiModel,
    val title: String,
    val content: String,
) : Parcelable
