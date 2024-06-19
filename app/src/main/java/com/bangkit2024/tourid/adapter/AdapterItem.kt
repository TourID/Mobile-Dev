package com.bangkit2024.tourid.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit2024.tourid.R
import com.bangkit2024.tourid.data.remote.response.TourResponseItem
import com.bangkit2024.tourid.databinding.ItemRowDestinationBinding
import com.bangkit2024.tourid.ui.detail.DetailActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class AdapterItem : ListAdapter<TourResponseItem, AdapterItem.MyViewHolder>(DIFF_CALLBACK) {

    class MyViewHolder(private val binding: ItemRowDestinationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userList: TourResponseItem) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(userList.imageUrl)
                    .apply(
                        RequestOptions
                            .centerCropTransform()
                            .placeholder(R.drawable.ic_refresh)
                            .error(R.drawable.ic_broken_image)
                    )
                    .into(imgItemPhoto)
                tvItemName.text = userList.placeName
                tvItemLocation.text = userList.city
                tvItemRating.text = userList.ratingLoc.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRowDestinationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        Log.d("AdapterItem", "Item at position $position: $user")
        if (user != null) {
            holder.bind(user)
        }

        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra(KEY_DETAIL, user.placeId)
            it.context.startActivity(intentDetail)
        }
    }

    companion object {
        const val KEY_DETAIL = "key_detail"

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TourResponseItem>() {
            override fun areItemsTheSame(oldItem: TourResponseItem, newItem: TourResponseItem): Boolean {
                return oldItem.placeId == newItem.placeId
            }

            override fun areContentsTheSame(oldItem: TourResponseItem, newItem: TourResponseItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}