package com.app.replace.ui.main.diary.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.replace.databinding.ItemDiaryWithProfileBinding
import com.app.replace.ui.main.diary.adapter.DiaryContentAdapter
import com.app.replace.ui.model.DiaryUiModel

class DiaryViewHolder private constructor(
    val binding: ItemDiaryWithProfileBinding,
    private val onClick: (Long) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var diaryContentAdapter: DiaryContentAdapter

    fun bind(diary: DiaryUiModel) {
        binding.diary = diary
        diaryContentAdapter = DiaryContentAdapter.from(diary, onClick)
        binding.rvDiary.adapter = diaryContentAdapter
        diaryContentAdapter.submitList(diary.contents)
    }

    companion object {
        fun from(
            parent: ViewGroup,
            onClick: (Long) -> Unit,
        ): DiaryViewHolder {
            val binding = ItemDiaryWithProfileBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
            return DiaryViewHolder(binding, onClick)
        }
    }
}
