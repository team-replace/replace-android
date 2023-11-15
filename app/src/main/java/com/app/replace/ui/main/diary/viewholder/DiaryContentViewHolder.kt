package com.app.replace.ui.main.diary.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.replace.databinding.ItemDiaryBinding
import com.app.replace.ui.main.diary.adapter.DiaryImageAdapter
import com.app.replace.ui.model.DiaryContentUiModel

class DiaryContentViewHolder private constructor(
    val binding: ItemDiaryBinding,
) : RecyclerView.ViewHolder(binding.root) {

    private val diaryImageAdapter by lazy {
        DiaryImageAdapter()
    }

    fun bind(diaryContent: DiaryContentUiModel) {
        binding.rvDiaryImage.adapter = diaryImageAdapter
    }

    companion object {
        fun from(
            parent: ViewGroup,
        ): DiaryContentViewHolder {
            val binding = ItemDiaryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
            return DiaryContentViewHolder(binding)
        }
    }
}
