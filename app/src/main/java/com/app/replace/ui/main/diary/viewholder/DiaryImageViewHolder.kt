package com.app.replace.ui.main.diary.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.replace.databinding.ItemDiaryImageBinding

class DiaryImageViewHolder private constructor(
    val binding: ItemDiaryImageBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(image: String) {
        binding.url = image
    }

    companion object {
        fun from(
            parent: ViewGroup,
        ): DiaryImageViewHolder {
            val binding = ItemDiaryImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
            return DiaryImageViewHolder(binding)
        }
    }
}
