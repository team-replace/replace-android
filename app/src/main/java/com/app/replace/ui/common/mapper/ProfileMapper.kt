package com.app.replace.ui.common.mapper

import com.app.replace.ui.model.CoupleProfileUiModel
import com.replace.data.model.response.ProfileResponse

fun ProfileResponse.toUi(): CoupleProfileUiModel {
    return CoupleProfileUiModel(
        user = this.user.toUi(),
        partner = this.partner?.toUi(),
    )
}
