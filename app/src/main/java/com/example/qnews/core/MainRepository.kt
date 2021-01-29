package com.example.qnews.core

import com.example.qnews.core.mapping.toNews
import java.lang.Exception

class MainRepository(
    val internetSource: NewsApiService
) {

    suspend fun getNewsFromNet() : List<News>? {
        try {
            val requestVO = internetSource.getNewsFromNet()

            val result = requestVO.results
            val articles = requestVO.articles

            return articles.map { it.toNews() }
        } catch (ex: Exception) {
            return null
        }
    }
}