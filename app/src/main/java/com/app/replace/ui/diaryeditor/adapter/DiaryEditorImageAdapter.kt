package com.app.replace.ui.diaryeditor.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.app.replace.ui.common.ImageDiffUtilCallback
import com.app.replace.ui.diaryeditor.viewholder.DiaryEditorImageViewHolder

class DiaryEditorImageAdapter(
    private val onItemDelete: (String) -> Unit,
) : ListAdapter<String, DiaryEditorImageViewHolder>(ImageDiffUtilCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryEditorImageViewHolder {
        return DiaryEditorImageViewHolder(parent, onItemDelete)
    }

    override fun onBindViewHolder(holder: DiaryEditorImageViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}
