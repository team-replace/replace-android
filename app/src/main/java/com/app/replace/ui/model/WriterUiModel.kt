package com.app.replace.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WriterUiModel(
    val profileImage: String,
    val nickname: String,
) : Parcelable
