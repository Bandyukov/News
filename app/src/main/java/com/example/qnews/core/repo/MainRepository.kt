package com.example.qnews.core.repo

import com.example.qnews.core.NewsApiService
import com.example.qnews.core.db.NewsDAO
import com.example.qnews.core.mapping.*
import com.example.qnews.core.models.news.News
import com.example.qnews.core.models.news.NewsRequestVO
import com.example.qnews.core.models.news.NewsVO
import com.example.qnews.core.models.suggestion.Search
import com.example.qnews.core.network.NewsParams

class MainRepository(
    private val internetSource: NewsApiService,
    private val localSource: NewsDAO
) {

    // API
    suspend fun getNewsFromNetAndCache() {
        try {
            val params = NewsParams(
                endpoint = "top-headlines",
                country = "us",
                pageSize = 50
            )

            val requestVO = internetSource.getNewsFromNet(
                endpoint = params.endpoint,
                params = params.toMap()
            )

            cache(requestVO)
        } catch (ex: Exception) {
            return
        }
    }

    suspend fun getNewsFromNetByTopic(topic: String) : List<News>? {
        return try {
            val params = NewsParams(
                endpoint = "everything",
                q = topic
            )

            val requestVO = internetSource.getNewsFromNetByTopic(
                endpoint = params.endpoint,
                params = params.toMap()
            )

            requestVO.articles.map { it.toNews() }
        } catch (ex: Exception) {
            null
        }
    }

    private suspend fun cache(requestVO: NewsRequestVO) {
        clearDatabase()

        val articles = requestVO.articles

        updateDatabase(articles)
    }


    // Room
    // News
    suspend fun getAllNewsFromDB() : List<News> = localSource.getAllNews().map { it.toNews() }

    suspend fun getCurrentNewsFromDB(newsId: Int) : News = localSource.getCurrentNews(newsId).toNews()

    private suspend fun updateDatabase(articles: List<NewsVO>) = localSource.insertNews(articles.map { it.toNewsDB() })

    private suspend fun clearDatabase() = localSource.deleteAllNews()

    // Suggestions
    suspend fun getAllRecentSearches() : List<Search> = localSource.getAllRecentSearch().map { it.toSearch() }

    suspend fun updateSearches(search: Search) = localSource.insertSearch(search.toSearchDB())

    suspend fun deleteSearch(search: Search) = localSource.deleteSearch(search.toSearchDB())

    suspend fun clearSearches() = localSource.deleteAllRecentSearch()
}