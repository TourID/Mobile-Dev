package com.bangkit2024.tourid.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit2024.tourid.R
import com.bangkit2024.tourid.data.remote.response.Recommendation
import com.bangkit2024.tourid.databinding.ItemRecommendationBinding
import com.bangkit2024.tourid.ui.detail.DetailActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class RecommendationAdapter : ListAdapter<Recommendation, RecommendationAdapter.RecommendationViewHolder>(DIFF_CALLBACK) {
    class RecommendationViewHolder(private val binding: ItemRecommendationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userList: Recommendation) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationViewHolder {
        val binding = ItemRecommendationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecommendationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecommendationViewHolder, position: Int) {
        val user = getItem(position)
        Log.d("RecommendationAdapter", "Item at position $position: $user")
        if (user != null) {
            holder.bind(user)
        }

        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra(AdapterItem.KEY_DETAIL, user.placeId)
            it.context.startActivity(intentDetail)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Recommendation>() {
            override fun areItemsTheSame(oldItem: Recommendation, newItem: Recommendation): Boolean {
                return oldItem.placeId == newItem.placeId
            }

            override fun areContentsTheSame(oldItem: Recommendation, newItem: Recommendation): Boolean {
                return oldItem == newItem
            }
        }
    }

}