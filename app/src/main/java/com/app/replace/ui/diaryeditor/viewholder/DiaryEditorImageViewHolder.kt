package com.app.replace.ui.diaryeditor.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.replace.R
import com.app.replace.databinding.ItemDiaryImageBinding

class DiaryEditorImageViewHolder(
    parent: ViewGroup,
    onItemDelete: (String) -> Unit,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(
        R.layout.item_diary_image,
        parent,
        false,
    ),
) {
    private val binding = ItemDiaryImageBinding.bind(itemView)

    init {
        binding.onItemDelete = onItemDelete
    }

    fun bind(imageUrl: String) {
        binding.url = imageUrl
    }
}
