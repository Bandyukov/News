package com.example.qnews.core

import com.example.qnews.core.models.NewsRequestVO
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


private const val BASE_URL = "https://newsapi.org/v2/"


private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface NewsApiService {
    @GET(value = "{endpoint}")
    suspend fun getNewsFromNet(
        @Path(value = "endpoint") endpoint: String,
        @Query(value = "country") country: String,
        @Query(value = "pageSize") pageSize: Int,
        @Query(value = "apiKey") apiKey: String
    ): NewsRequestVO

    @GET(value = "{endpoint}")
    suspend fun getNewsFromNetByTopic(
        @Path(value = "endpoint") endpoint: String,
        @Query(value = "q") q: String,
        @Query(value = "apiKey") apiKey: String
    ): NewsRequestVO
}

object NewsApi {
    val NewsApiService by lazy {
        retrofit.create(NewsApiService::class.java)
    }
}