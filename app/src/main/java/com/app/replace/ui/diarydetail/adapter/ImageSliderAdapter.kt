package com.app.replace.ui.diarydetail.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.replace.ui.diarydetail.viewholder.ImageSliderViewHolder

class ImageSliderAdapter(
    private val images: List<String>,
) : RecyclerView.Adapter<ImageSliderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSliderViewHolder {
        return ImageSliderViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ImageSliderViewHolder, position: Int) {
        return holder.bind(images[position])
    }
}
