package com.app.replace.ui.main.diary.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.app.replace.ui.main.diary.viewholder.DiaryContentViewHolder
import com.app.replace.ui.model.DiaryContentUiModel

class DiaryContentAdapter :
    ListAdapter<DiaryContentUiModel, DiaryContentViewHolder>(DiaryContentDiffUtilCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryContentViewHolder {
        return DiaryContentViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: DiaryContentViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}
