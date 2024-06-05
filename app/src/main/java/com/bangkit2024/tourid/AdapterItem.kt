package com.bangkit2024.tourid

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit2024.tourid.data.ProductsItem
import com.bangkit2024.tourid.databinding.ItemRowDestinationBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class AdapterItem: ListAdapter<ProductsItem, AdapterItem.MyViewHolder>(DIFF_CALLBACK) {

    class MyViewHolder(private val binding: ItemRowDestinationBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(itemList: ProductsItem) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(itemList.images)
                    .apply(
                        RequestOptions
                            .centerCropTransform()
                            .placeholder(R.drawable.ic_refresh)
                            .error(R.drawable.ic_broken_image)
                    )
                    .into(imgItemPhoto)
                tvItemName.text = itemList.title
                tvItemRating.text = itemList.rating.toString()
                tvItemLocation.text = itemList.category
            }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val bind = ItemRowDestinationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(bind)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProductsItem>() {
            override fun areItemsTheSame(
                oldItem: ProductsItem,
                newItem: ProductsItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ProductsItem,
                newItem: ProductsItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}