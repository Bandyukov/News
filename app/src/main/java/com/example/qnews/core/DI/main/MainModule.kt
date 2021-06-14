package com.example.qnews.core.DI.main

import com.example.qnews.core.NewsApiService
import com.example.qnews.core.db.NewsDAO
import com.example.qnews.core.repo.MainRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
abstract class MainModule {
    companion object {
        @Provides
        @MainScope
        fun provideNewsApi(retrofit: Retrofit) : NewsApiService =
            retrofit.create(NewsApiService::class.java)

        @Provides
        @MainScope
        fun provideRepo(dao: NewsDAO, api: NewsApiService) : MainRepository =
            MainRepository(api, dao)
    }
}