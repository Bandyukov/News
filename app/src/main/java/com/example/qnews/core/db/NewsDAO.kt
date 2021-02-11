package com.example.qnews.core.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsDAO {
    @Query("SELECT * FROM the_news_table")
    suspend fun getAllNews() : List<NewsDB>

    @Query("SELECT * FROM the_news_table WHERE uniqueId==:newsId")
    suspend fun getCurrentNews(newsId: Int) : NewsDB

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(newsList: List<NewsDB>)

    @Query("DELETE FROM the_news_table")
    suspend fun deleteAllNews()
}