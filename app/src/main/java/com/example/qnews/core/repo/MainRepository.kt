package com.example.qnews.core.repo

import com.example.qnews.core.models.News
import com.example.qnews.core.NewsApiService
import com.example.qnews.core.models.NewsVO
import com.example.qnews.core.db.NewsDAO
import com.example.qnews.core.mapping.toNews
import com.example.qnews.core.mapping.toNewsDB
import java.lang.Exception

class MainRepository(
    private val internetSource: NewsApiService,
    private val localSource: NewsDAO
) {

    companion object {
        var flag = true
    }

    suspend fun getNewsFromNet() {
        try {
            val requestVO = internetSource.getNewsFromNet("top-headlines", "us", 50, "1855f8b714864e30be7a1e57b5d8855b")

            cleatDatabase()

            val result = requestVO.results
            val articles = requestVO.articles

            updateDatabase(articles)

        } catch (ex: Exception) {
            return
        }
    }

    suspend fun getAllNewsFromDB() : List<News> = localSource.getAllNews().map { it.toNews() }

    suspend fun getCurrentNewsFromDB(newsId: Int) : News = localSource.getCurrentNews(newsId).toNews()

    private suspend fun updateDatabase(articles: List<NewsVO>) = localSource.insertNews(articles.map { it.toNewsDB() })

    suspend fun cleatDatabase() = localSource.deleteAllNews()
}