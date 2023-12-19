package com.app.replace.ui.common.mapper

import com.app.replace.ui.model.DateUiModel
import com.app.replace.ui.model.DiaryContentUiModel
import com.app.replace.ui.model.DiaryDetailUiModel
import com.app.replace.ui.model.DiaryUiModel
import com.app.replace.ui.model.PlaceDiaryUiModel
import com.replace.data.model.response.DiaryContentResponse
import com.replace.data.model.response.DiaryDetailResponse
import com.replace.data.model.response.DiaryResponse

fun DiaryDetailResponse.toUi(): DiaryDetailUiModel {
    return DiaryDetailUiModel(
        id = this.id,
        images = this.images,
        place = this.place.toUi(),
        createdAt = DateUiModel(createdAt).getDate(),
        writer = this.writer.toUi(),
        title = this.title,
        content = this.content,
    )
}

fun DiaryContentResponse.toUi(): DiaryContentUiModel {
    return DiaryContentUiModel(
        id = this.id,
        title = this.title,
        thumbnails = this.thumbnails,
        numOfExtraThumbnails = this.numOfExtraThumbnails,
        createdAt = DateUiModel(createdAt).getDate(),
    )
}

fun DiaryResponse.toUi(): DiaryUiModel {
    return DiaryUiModel(user = this.user.toUi(), contents = this.contents.map { it.toUi() })
}

fun PlaceDiaryUiModel.toDiaryContentUiModel(): DiaryContentUiModel {
    return DiaryContentUiModel(
        id = this.id,
        title = this.title,
        thumbnails = this.thumbnails,
        numOfExtraThumbnails = this.numOfExtraThumbnails,
        createdAt = DateUiModel(createdAt).getDate(),
    )
}
