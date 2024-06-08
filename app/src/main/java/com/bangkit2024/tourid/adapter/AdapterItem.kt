package com.bangkit2024.tourid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit2024.tourid.R
import com.bangkit2024.tourid.data.GithubUsersResponseItem
import com.bangkit2024.tourid.databinding.ItemRowDestinationBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class AdapterItem : ListAdapter<GithubUsersResponseItem, AdapterItem.MyViewHolder>(DIFF_CALLBACK) {

    class MyViewHolder(private val binding: ItemRowDestinationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userList: GithubUsersResponseItem) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(userList.avatarUrl)
                    .apply(
                        RequestOptions
                            .centerCropTransform()
                            .placeholder(R.drawable.ic_refresh)
                            .error(R.drawable.ic_broken_image)
                    )
                    .into(imgItemPhoto)
                tvItemName.text = userList.login
                tvItemLocation.text = userList.name
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
    }

    companion object {
        private const val KEY_GITHUB = "key_github"

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GithubUsersResponseItem>() {
            override fun areItemsTheSame(
                oldItem: GithubUsersResponseItem,
                newItem: GithubUsersResponseItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: GithubUsersResponseItem,
                newItem: GithubUsersResponseItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}