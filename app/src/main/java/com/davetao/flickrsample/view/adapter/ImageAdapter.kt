package com.davetao.flickrsample.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.davetao.flickrsample.databinding.ViewListImageBinding
import com.davetao.flickrsample.viewmodel.model.ListImage

class ImageAdapter: RecyclerView.Adapter<ImageAdapter.ImageHolder>() {

    var items = listOf<ListImage>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val binding = ViewListImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageHolder(binding)
    }


    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.binding.listImage = items[position]
    }

    class ImageHolder (val binding: ViewListImageBinding): RecyclerView.ViewHolder(binding.root)
}