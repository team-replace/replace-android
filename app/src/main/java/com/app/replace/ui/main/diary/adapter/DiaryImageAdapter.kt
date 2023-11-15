package com.app.replace.ui.main.diary.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.app.replace.ui.common.ImageDiffUtilCallback
import com.app.replace.ui.main.diary.viewholder.DiaryImageViewHolder

class DiaryImageAdapter : ListAdapter<String, DiaryImageViewHolder>(ImageDiffUtilCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryImageViewHolder {
        return DiaryImageViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: DiaryImageViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}
