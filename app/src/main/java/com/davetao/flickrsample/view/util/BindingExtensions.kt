package com.davetao.flickrsample.view.util

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.davetao.flickrsample.R
import com.squareup.picasso.Picasso

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
    if (!url.isNullOrBlank()) {
        Picasso.get()
                .load(url)
                .into(imageView)
    }
}
