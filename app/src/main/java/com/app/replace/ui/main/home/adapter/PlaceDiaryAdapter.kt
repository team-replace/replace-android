package com.app.replace.ui.main.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.app.replace.ui.main.home.viewholder.PlaceDiaryViewHolder
import com.app.replace.ui.model.PlaceDiaryUiModel

class PlaceDiaryAdapter(
    private val onClick: (Long) -> Unit,
) :
    ListAdapter<PlaceDiaryUiModel, PlaceDiaryViewHolder>(PlaceDiaryDiffUtillCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceDiaryViewHolder {
        return PlaceDiaryViewHolder.from(parent, onClick)
    }

    override fun onBindViewHolder(holder: PlaceDiaryViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}
