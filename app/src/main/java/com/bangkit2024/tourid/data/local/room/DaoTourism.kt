package com.bangkit2024.tourid.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bangkit2024.tourid.data.remote.response.TourResponseItem

//@Dao
//interface DaoTourism{
//    @Query("SELECT * FROM tourResponse")
//    fun getTourism(): PagingSource<Int, TourResponseItem>
//
////    @Query("SELECT * FROM tourism where bookmarked = 1")
////    fun getBookmarkedTourism(): LiveData<List<EntityTourism>>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertTourism(tourism: List<TourResponseItem>)
//
//    @Update
//    suspend fun updateTourism(tourism: TourResponseItem)
//
//    @Query("DELETE FROM tourResponse")
//    suspend fun deleteAll()
//
////    @Query("SELECT EXISTS(SELECT * FROM tourism WHERE placeName = :placeName AND bookmarked = 1)")
////    suspend fun isNewsTourism(placeName: String): Boolean
//}