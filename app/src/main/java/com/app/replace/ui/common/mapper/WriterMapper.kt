package com.app.replace.ui.common.mapper

import com.app.replace.ui.model.WriterUiModel
import com.replace.data.model.response.WriterResponse

fun WriterResponse.toUi(): WriterUiModel {
    return WriterUiModel(
        profileImage = this.profileImage,
        nickname = this.nickname,
    )
}
