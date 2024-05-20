package com.example.funprimetask.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.funprimetask.databinding.ItemRecViewPhotosBinding
import com.example.funprimetask.models.PhotosDataModel

class MainPhotosAdapter(
    private val context: Context,
    private val dataList: List<PhotosDataModel>,
    val callback: (PhotosDataModel) -> Unit
) : RecyclerView.Adapter<MainPhotosAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemRecViewPhotosBinding) : RecyclerView.ViewHolder(binding.root)
    {
        init {
            binding.clRecViewMainItem.setOnClickListener {
                callback(dataList[position])
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRecViewPhotosBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = dataList[position]

        Glide.with(context)
            .load(currentItem.thumbnailUrl)
            .centerCrop()
            .into(holder.binding.imgThumbNail)

        holder.binding.txtPhotoTitle.text = currentItem.title

    }


}