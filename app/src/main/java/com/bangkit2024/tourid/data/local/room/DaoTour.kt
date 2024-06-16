package com.bangkit2024.tourid.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bangkit2024.tourid.data.local.entity.EntityTourism

@Dao
interface DaoTourism{
    @Query("SELECT * FROM tourism ORDER BY rating DESC")
    fun getTourism(): LiveData<List<EntityTourism>>

    @Query("SELECT * FROM tourism where bookmarked = 1")
    fun getBookmarkedTourism(): LiveData<List<EntityTourism>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTourism(tourism: List<EntityTourism>)

    @Update
    suspend fun updateTourism(tourism: EntityTourism)

    @Query("DELETE FROM tourism WHERE bookmarked = 0")
    suspend fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM tourism WHERE placeName = :placeName AND bookmarked = 1)")
    suspend fun isNewsTourism(placeName: String): Boolean
}