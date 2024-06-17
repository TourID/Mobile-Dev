package com.bangkit2024.tourid.data.remote.response

import com.google.gson.annotations.SerializedName

data class TourResponse(

	@field:SerializedName("TourResponse")
	val tourResponse: List<TourResponseItem> = emptyList()
)

data class TourResponseItem(

	@field:SerializedName("placeId")
	val placeId: Int? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("ratingLoc")
	val ratingLoc: Float? = null,

	@field:SerializedName("category")
	val category: String? = null,

	@field:SerializedName("placeName")
	val placeName: String? = null
)