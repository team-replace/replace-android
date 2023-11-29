package com.app.replace.ui.main.diary.adapter

import androidx.recyclerview.widget.DiffUtil
import com.app.replace.ui.model.DiaryContentUiModel

object DiaryContentDiffUtilCallback : DiffUtil.ItemCallback<DiaryContentUiModel>() {
    override fun areItemsTheSame(
        oldItem: DiaryContentUiModel,
        newItem: DiaryContentUiModel,
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: DiaryContentUiModel,
        newItem: DiaryContentUiModel,
    ): Boolean {
        return oldItem == newItem
    }
}
