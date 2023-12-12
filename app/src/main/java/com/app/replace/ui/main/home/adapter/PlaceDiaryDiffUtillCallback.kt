package com.app.replace.ui.main.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.app.replace.ui.model.PlaceDiaryUiModel

object PlaceDiaryDiffUtillCallback : DiffUtil.ItemCallback<PlaceDiaryUiModel>() {
    override fun areItemsTheSame(oldItem: PlaceDiaryUiModel, newItem: PlaceDiaryUiModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: PlaceDiaryUiModel,
        newItem: PlaceDiaryUiModel,
    ): Boolean {
        return oldItem == newItem
    }
}
