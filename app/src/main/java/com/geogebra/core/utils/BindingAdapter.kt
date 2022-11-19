package com.geogebra.core.utils

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


object BindingAdapter {

    @JvmStatic
    @BindingAdapter("image_url")
    fun AppCompatImageView.setImageUrl(url : String?){
        if (!url.isNullOrEmpty()) {
            Glide.with(this).load(url).centerCrop().into(this)
        }
    }
}