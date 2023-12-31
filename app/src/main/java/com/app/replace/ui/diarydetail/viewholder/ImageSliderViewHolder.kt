package com.app.replace.ui.diarydetail.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.replace.R
import com.app.replace.databinding.ItemImageSliderBinding

class ImageSliderViewHolder(
    parent: ViewGroup,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_image_slider, parent, false),
) {
    private val binding = ItemImageSliderBinding.bind(itemView)

    fun bind(imageUrl: String) {
        binding.imageUrl = imageUrl
    }
}
