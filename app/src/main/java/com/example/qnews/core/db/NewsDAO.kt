package com.example.qnews.core.db

import androidx.room.*
import com.example.qnews.core.db.entities.NewsDB
import com.example.qnews.core.db.entities.SearchDB

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



    @Query("SELECT * FROM recent_search_table ORDER BY -`key`")
    suspend fun getAllRecentSearch() : List<SearchDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearch(searchDB: SearchDB)

    @Query("DELETE FROM recent_search_table")
    suspend fun deleteAllRecentSearch()

    @Delete
    suspend fun deleteSearch(searchDB: SearchDB)
}