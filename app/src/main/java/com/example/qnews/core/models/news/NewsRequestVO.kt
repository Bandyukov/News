package com.example.qnews.core.models.news

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsRequestVO(
    @Json(name = "totalResults")
    val results: Int,

    @Json(name = "articles")
    val articles: List<NewsVO>
) : Parcelable