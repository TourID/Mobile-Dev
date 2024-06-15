package com.bangkit2024.tourid.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bangkit2024.tourid.data.local.entity.EntityTourism

//@Database(entities = [EntityTour::class], version = 1, exportSchema = false)
//abstract class DatabaseTour: RoomDatabase() {
//
//    abstract fun bookmarkDao(): DaoTour
//
//    companion object {
//        @Volatile
//        private var instance: DatabaseTour? = null
//        fun getInstance(context: Context): DatabaseTour =
//            instance ?: synchronized(this) {
//                instance ?: Room.databaseBuilder(
//                    context.applicationContext,
//                    DatabaseTour::class.java, "News.db"
//                ).build()
//            }
//    }
//}

@Database(entities = [EntityTourism::class], version = 1, exportSchema = false)
abstract class DatabaseTourism: RoomDatabase() {

    abstract fun bookmarkDao(): DaoTourism

    companion object {
        @Volatile
        private var instance: DatabaseTourism? = null
        fun getInstance(context: Context): DatabaseTourism =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseTourism::class.java, "tour.db"
                ).build()
            }
    }
}