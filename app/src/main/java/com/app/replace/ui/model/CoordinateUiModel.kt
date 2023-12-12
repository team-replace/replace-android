package com.app.replace.ui.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CoordinateUiModel(
    val latitude: String,
    val longitude: String,
) : Parcelable
