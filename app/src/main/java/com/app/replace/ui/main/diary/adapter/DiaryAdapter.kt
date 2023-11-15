package com.app.replace.ui.main.diary.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.app.replace.ui.main.diary.viewholder.DiaryViewHolder
import com.app.replace.ui.model.DiaryUiModel

class DiaryAdapter : ListAdapter<DiaryUiModel, DiaryViewHolder>(DiaryDiffUtilCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {
        return DiaryViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}
