package com.app.replace.ui.common.mapper

import com.app.replace.ui.model.DateUiModel
import com.app.replace.ui.model.DiaryUiModel
import com.replace.data.model.response.DiaryDetailResponse

fun DiaryDetailResponse.toUi(): DiaryUiModel {
    return DiaryUiModel(
        id = this.id,
        images = this.images,
        place = this.place.toUi(),
        createdAt = DateUiModel(createdAt).getDate(),
        writer = this.writer.toUi(),
        title = this.title,
        content = this.content,
    )
}
