package com.example.qnews.core

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.GET


private const val BASE_URL = "https://newsapi.org/v2/"


private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface NewsApiService {
    @GET(value = "top-headlines?country=us&apiKey=1855f8b714864e30be7a1e57b5d8855b")
    suspend fun getNewsFromNet() : NewsRequestVO
}

object NewsApi {
    val NewsApiService by lazy {
        retrofit.create(NewsApiService::class.java)
    }
}