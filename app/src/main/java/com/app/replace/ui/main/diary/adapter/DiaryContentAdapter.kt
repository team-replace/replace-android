package com.app.replace.ui.main.diary.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.app.replace.ui.main.diary.viewholder.DiaryContentViewHolder
import com.app.replace.ui.model.DiaryContentUiModel
import com.app.replace.ui.model.DiaryUiModel
import com.app.replace.ui.model.WriterUiModel

class DiaryContentAdapter private constructor(
    private val user: WriterUiModel,
) :
    ListAdapter<DiaryContentUiModel, DiaryContentViewHolder>(DiaryContentDiffUtilCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryContentViewHolder {
        return DiaryContentViewHolder.from(parent, user)
    }

    override fun onBindViewHolder(holder: DiaryContentViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        fun from(diary: DiaryUiModel): DiaryContentAdapter = DiaryContentAdapter(diary.user)
    }
}
