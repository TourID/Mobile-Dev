package com.bangkit2024.tourid.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity(tableName = "news")
//class EntityTour(
//    @field:ColumnInfo(name = "title")
//    @field:PrimaryKey
//    val title: String,
//
//    @field:ColumnInfo(name = "publishedAt")
//    val publishedAt: String,
//
//    @field:ColumnInfo(name = "urlToImage")
//    val urlToImage: String? = null,
//
//    @field:ColumnInfo(name = "url")
//    val url: String? = null,
//
//    @field:ColumnInfo(name = "bookmarked")
//    var isBookmarked: Boolean,
//
//    @field:ColumnInfo(name = "bookmarkedAt")
//    val bookmarkedAt: Long = System.currentTimeMillis()
//)

@Entity(tableName = "tourism")
class EntityTourism(

    @field:ColumnInfo(name = "placeId")
    @field:PrimaryKey
    val placeId: Int,

    @field:ColumnInfo(name = "category")
    val category: String? = null,

    @field:ColumnInfo(name = "imageUrl")
    val imageUrl: String? = null,

    @field:ColumnInfo(name = "placeName")
    val placeName: String,

    @field:ColumnInfo(name = "rating")
    val rating: Float? = null,

    @field:ColumnInfo(name = "city")
    val city: String? = null,

    @field:ColumnInfo(name = "bookmarked")
    var isBookmarked: Boolean,
)