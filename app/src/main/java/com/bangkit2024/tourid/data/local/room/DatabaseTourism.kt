package com.bangkit2024.tourid.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bangkit2024.tourid.data.remote.response.TourResponseItem

//@Database(entities = [TourResponseItem::class], version = 1, exportSchema = false)
//abstract class DatabaseTourism: RoomDatabase() {
//
//    abstract fun tourDao(): DaoTourism
//
//    companion object {
//        @Volatile
//        private var instance: DatabaseTourism? = null
//
//        fun getInstance(context: Context): DatabaseTourism =
//            instance ?: synchronized(this) {
//                instance ?: Room.databaseBuilder(
//                    context.applicationContext,
//                    DatabaseTourism::class.java, "tour.db"
//                ).build()
//            }
//    }
//}