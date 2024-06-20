package com.bangkit2024.tourid.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserRequest(
    val userId: String
)

data class Recommendation(
    @field:SerializedName("category")
    val category: String,

    @field:SerializedName("city")
    val city: String,

    @field:SerializedName("imageUrl")
    val imageUrl: String,

    @field:SerializedName("placeId")
    val placeId: Int,

    @field:SerializedName("placeName")
    val placeName: String,

    @field:SerializedName("ratingLoc")
    val ratingLoc: Double
)
