package com.example.qnews.core

import com.example.qnews.core.models.news.NewsRequestVO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap


interface NewsApiService {
    @GET(value = "{endpoint}")
    suspend fun getNewsFromNet(
        @Path(value = "endpoint") endpoint: String,
        @QueryMap params: Map<String, String>
    ): NewsRequestVO

    @GET(value = "{endpoint}")
    suspend fun getNewsFromNetByTopic(
        @Path(value = "endpoint") endpoint: String,
        @QueryMap params: Map<String, String>
    ): NewsRequestVO
}
