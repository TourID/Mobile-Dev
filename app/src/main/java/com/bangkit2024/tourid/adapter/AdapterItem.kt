package com.bangkit2024.tourid.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit2024.tourid.R
import com.bangkit2024.tourid.data.local.entity.EntityTourism
import com.bangkit2024.tourid.databinding.ItemRowDestinationBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

//class AdapterItem(private val onBookmarkClick: (EntityTour) -> Unit) : ListAdapter<EntityTour, AdapterItem.MyViewHolder>(DIFF_CALLBACK) {
//
//    class MyViewHolder(val binding: ItemRowDestinationBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        fun bind(userList: EntityTour) {
//            binding.apply {
//                Glide.with(itemView.context)
//                    .load(userList.urlToImage)
//                    .apply(
//                        RequestOptions
//                            .centerCropTransform()
//                            .placeholder(R.drawable.ic_refresh)
//                            .error(R.drawable.ic_broken_image)
//                    )
//                    .into(imgItemPhoto)
//                tvItemName.text = userList.title
//                tvItemLocation.text =DateFormatter.formatDate(userList.publishedAt)
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val binding = ItemRowDestinationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return MyViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val user = getItem(position)
//        holder.bind(user)
//
//        val ivBookmark = holder.binding.ivBookmark
//        if (user.isBookmarked) {
//            ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.context, R.drawable.ic_bookmark_24))
//        } else {
//            ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.context, R.drawable.ic_bookmark_border))
//        }
//        ivBookmark.setOnClickListener {
//            onBookmarkClick(user)
//        }
//    }
//
//    companion object {
//        private const val KEY_GITHUB = "key_github"
//
//        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<EntityTour>() {
//            override fun areItemsTheSame(
//                oldItem: EntityTour,
//                newItem: EntityTour
//            ): Boolean {
//                return oldItem.title == newItem.title
//            }
//
//            @SuppressLint("DiffUtilEquals")
//            override fun areContentsTheSame(
//                oldItem: EntityTour,
//                newItem: EntityTour
//            ): Boolean {
//                return oldItem == newItem
//            }
//
//        }
//    }
//}

class AdapterItem(private val onBookmarkClick: (EntityTourism) -> Unit) : ListAdapter<EntityTourism, AdapterItem.MyViewHolder>(DIFF_CALLBACK) {

    class MyViewHolder(val binding: ItemRowDestinationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userList: EntityTourism) {
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
                tvItemRating.text = userList.rating.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRowDestinationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)

        val ivBookmark = holder.binding.ivBookmark
        if (user.isBookmarked) {
            ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.context, R.drawable.ic_bookmark_24))
        } else {
            ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.context, R.drawable.ic_bookmark_border))
        }
        ivBookmark.setOnClickListener {
            onBookmarkClick(user)
        }
    }

    companion object {
        private const val KEY_GITHUB = "key_github"

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<EntityTourism>() {
            override fun areItemsTheSame(
                oldItem: EntityTourism,
                newItem: EntityTourism
            ): Boolean {
                return oldItem.placeId == newItem.placeId
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: EntityTourism,
                newItem: EntityTourism
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}