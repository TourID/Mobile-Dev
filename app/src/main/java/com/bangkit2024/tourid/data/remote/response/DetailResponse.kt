package com.bangkit2024.tourid.data.remote.response

import com.google.gson.annotations.SerializedName

data class DetailResponse(

	@field:SerializedName("reviews")
	val reviews: List<ReviewsItem?>? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("price")
	val price: String? = null,

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("ratingLoc")
	val ratingLoc: Any? = null,

	@field:SerializedName("category")
	val category: String? = null,

	@field:SerializedName("placeName")
	val placeName: String? = null,

	@field:SerializedName("lat")
	val lat: Any? = null,

	@field:SerializedName("long")
	val jsonMemberLong: Any? = null
)

data class ReviewsItem(

	@field:SerializedName("review")
	val review: String? = null,

	@field:SerializedName("placeId")
	val placeId: Int? = null,

	@field:SerializedName("rating")
	val rating: Int? = null,

	@field:SerializedName("uid_model")
	val uidModel: Int? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)
