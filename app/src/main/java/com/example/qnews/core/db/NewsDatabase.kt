package com.example.qnews.core.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NewsDB::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {

    abstract val newsDao: NewsDAO

    companion object {
        const val DATABASE_NAME = "news.db"

        @Volatile
        private var INSTANCE: NewsDatabase? = null

        fun getInstance(context: Context) : NewsDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        NewsDatabase::class.java,
                        DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}