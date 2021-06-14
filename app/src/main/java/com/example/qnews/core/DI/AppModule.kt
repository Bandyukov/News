package com.example.qnews.core.DI

import android.app.Application
import com.bumptech.glide.request.RequestOptions
import com.example.qnews.R
import com.example.qnews.core.DI.resources.AndroidResourceProvider
import com.example.qnews.core.DI.resources.ResourceProvider
import com.example.qnews.core.db.NewsDAO
import com.example.qnews.core.db.NewsDatabase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
abstract class AppModule {

    companion object {
        private const val BASE_URL = "https://newsapi.org/v2/"

        @Provides
        @Singleton
        fun provideMoshi() : Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        @Provides
        @Singleton
        fun provideRetrofit(moshi: Moshi) : Retrofit =
            Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(BASE_URL)
                .build()

        @Provides
        @Singleton
        fun provideDao(application: Application) : NewsDAO =
            NewsDatabase.getInstance(application).newsDao

        @Provides
        @Singleton
        fun provideRequestOptions() : RequestOptions =
            RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.connection_error_image)
    }

    @Binds
    @Singleton
    abstract fun bindResources(androidResourceProvider: AndroidResourceProvider) : ResourceProvider
}