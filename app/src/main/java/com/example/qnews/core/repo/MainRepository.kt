package com.example.qnews.core.repo

import com.example.qnews.core.models.news.News
import com.example.qnews.core.NewsApiService
import com.example.qnews.core.models.news.NewsVO
import com.example.qnews.core.db.NewsDAO
import com.example.qnews.core.mapping.toNews
import com.example.qnews.core.mapping.toNewsDB
import com.example.qnews.core.models.news.NewsRequestVO
import java.lang.Exception

class MainRepository(
    private val internetSource: NewsApiService,
    private val localSource: NewsDAO
) {

    companion object {
        var flag = true
        const val apiKey = "1855f8b714864e30be7a1e57b5d8855b"
    }

    // API
    suspend fun getNewsFromNetAndCache() {
        try {
            val requestVO = internetSource.getNewsFromNet("top-headlines", "us", 50, apiKey)
            cache(requestVO)
        } catch (ex: Exception) {
            return
        }
    }

    suspend fun getNewsFromNet() : List<News>? {
        return try {
            val requestVO = internetSource.getNewsFromNet("top-headlines", "us", 50, apiKey)
            requestVO.articles.map { it.toNews() }
        } catch (ex: Exception) {
            null
        }
    }

    suspend fun getNewsFromNetByTopic(topic: String) : List<News>? {
        return try {
            val requestVO = internetSource.getNewsFromNetByTopic("everything", topic, apiKey)
            requestVO.articles.map { it.toNews() }
        } catch (ex: Exception) {
            null
        }
    }

    private suspend fun cache(requestVO: NewsRequestVO) {
        clearDatabase()

        val result = requestVO.results
        val articles = requestVO.articles

        updateDatabase(articles)
    }


    // Room
    suspend fun getAllNewsFromDB() : List<News> = localSource.getAllNews().map { it.toNews() }

    suspend fun getCurrentNewsFromDB(newsId: Int) : News = localSource.getCurrentNews(newsId).toNews()

    private suspend fun updateDatabase(articles: List<NewsVO>) = localSource.insertNews(articles.map { it.toNewsDB() })

    private suspend fun clearDatabase() = localSource.deleteAllNews()
}