package com.app.replace.ui.main.diary.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.replace.databinding.ItemDiaryWithProfileBinding
import com.app.replace.ui.main.diary.adapter.DiaryContentAdapter
import com.app.replace.ui.model.DiaryUiModel

class DiaryViewHolder private constructor(
    val binding: ItemDiaryWithProfileBinding,
) : RecyclerView.ViewHolder(binding.root) {

    private val diaryContentAdapter by lazy {
        DiaryContentAdapter()
    }

    fun bind(diary: DiaryUiModel) {
        binding.rvDiary.adapter = diaryContentAdapter
    }

    companion object {
        fun from(
            parent: ViewGroup,
        ): DiaryViewHolder {
            val binding = ItemDiaryWithProfileBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
            return DiaryViewHolder(binding)
        }
    }
}
