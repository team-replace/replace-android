package com.app.replace.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlaceUiModel(
    val spotName: String,
    val roadAddress: String,
) : Parcelable
