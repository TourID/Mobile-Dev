package com.bangkit2024.tourid.data.remote.response

import com.google.gson.annotations.SerializedName

data class TourResponseAll(

	@field:SerializedName("TourResponseAll")
	val tourResponseAll: List<TourResponseAllItem>
)

data class TourResponseAllItem(

	@field:SerializedName("Category")
	val category: String,

	@field:SerializedName("Image_URL")
	val imageURL: String,

	@field:SerializedName("Place_Id")
	val placeId: Int,

	@field:SerializedName("Place_Name")
	val placeName: String,

	@field:SerializedName("Rating")
	val rating: Float,

	@field:SerializedName("City")
	val city: String
)
