package com.bangkit2024.tourid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit2024.tourid.R
import com.bangkit2024.tourid.data.remote.response.ReviewsItem

class ReviewAdapter : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    private val reviewsList = mutableListOf<ReviewsItem>()

    fun submitList(reviews: List<ReviewsItem>) {
        reviewsList.clear()
        reviewsList.addAll(reviews)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reviews, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val reviewItem = reviewsList[position]
        holder.bind(reviewItem)
    }

    override fun getItemCount(): Int = reviewsList.size

    class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvUsername: TextView = itemView.findViewById(R.id.tvUsername)
        private val tvRating: TextView = itemView.findViewById(R.id.tvRating)
        private val tvReview: TextView = itemView.findViewById(R.id.tvReview)

        fun bind(reviewItem: ReviewsItem) {
            tvUsername.text = reviewItem.username
            tvRating.text = "Rating: ${reviewItem.rating?.toFloat() ?: 0f}"
            tvReview.text = reviewItem.review
        }
    }
}
