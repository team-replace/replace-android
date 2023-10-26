package com.app.replace.ui.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.app.replace.R
import com.bumptech.glide.Glide

object CustomBindingAdapter {

    @BindingAdapter("imgUrlCircleCrop")
    @JvmStatic
    fun setCircleCropImageResource(view: ImageView, url: String?) {
        Glide.with(view)
            .load(url)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .circleCrop()
            .into(view)
    }
}
