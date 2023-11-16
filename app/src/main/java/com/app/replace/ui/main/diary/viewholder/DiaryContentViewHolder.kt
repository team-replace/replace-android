package com.app.replace.ui.main.diary.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.replace.databinding.ItemDiaryBinding
import com.app.replace.ui.main.diary.adapter.DiaryImageAdapter
import com.app.replace.ui.model.DiaryContentUiModel
import com.app.replace.ui.model.WriterUiModel

class DiaryContentViewHolder private constructor(
    val binding: ItemDiaryBinding,
    user: WriterUiModel,
) : RecyclerView.ViewHolder(binding.root) {

    private val diaryImageAdapter by lazy {
        DiaryImageAdapter()
    }

    init {
        binding.user = user
        binding.rvDiaryImage.adapter = diaryImageAdapter
    }

    fun bind(diaryContent: DiaryContentUiModel) {
        diaryImageAdapter.submitList(diaryContent.thumbnails)
    }

    companion object {
        fun from(
            parent: ViewGroup,
            user: WriterUiModel,
        ): DiaryContentViewHolder {
            val binding = ItemDiaryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
            return DiaryContentViewHolder(binding, user)
        }
    }
}
