package com.app.replace.ui.main.diary.adapter

import androidx.recyclerview.widget.DiffUtil
import com.app.replace.ui.model.DiaryUiModel

object DiaryDiffUtilCallback : DiffUtil.ItemCallback<DiaryUiModel>() {
    override fun areItemsTheSame(oldItem: DiaryUiModel, newItem: DiaryUiModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: DiaryUiModel, newItem: DiaryUiModel): Boolean {
        return oldItem.user == newItem.user
    }
}
