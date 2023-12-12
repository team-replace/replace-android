package com.app.replace.ui.main.home.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.replace.databinding.ItemDiaryBinding
import com.app.replace.ui.common.mapper.toDiaryContentUiModel
import com.app.replace.ui.main.diary.adapter.DiaryImageAdapter
import com.app.replace.ui.model.PlaceDiaryUiModel

class PlaceDiaryViewHolder private constructor(
    val binding: ItemDiaryBinding,
    onClick: (Long) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    private val diaryImageAdapter by lazy {
        DiaryImageAdapter()
    }

    init {
        binding.onClick = onClick
        binding.rvDiaryImage.adapter = diaryImageAdapter
    }

    fun bind(placeDiary: PlaceDiaryUiModel) {
        binding.user = placeDiary.user
        binding.content = placeDiary.toDiaryContentUiModel()
        diaryImageAdapter.submitList(placeDiary.thumbnails)
    }

    companion object {
        fun from(
            parent: ViewGroup,
            onClick: (Long) -> Unit,
        ): PlaceDiaryViewHolder {
            val binding = ItemDiaryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
            return PlaceDiaryViewHolder(binding, onClick)
        }
    }
}
