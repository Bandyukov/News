package com.example.qnews.core

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsVO(
    @Json(name = "source")
    val source: Source,

    @Json(name = "author")
    val author: String?,

    @Json(name = "title")
    val title: String,

    @Json(name = "description")
    val description: String?,

    @Json(name = "url")
    val url: String,

    @Json(name = "urlToImage")
    val urlToImage: String?,

    @Json(name = "publishedAt")
    val publishedAt: String,

    @Json(name = "content")
    val content: String?
) : Parcelable

