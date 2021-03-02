package com.example.qnews.core.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Source(
    @Json(name = "id")
    val id: String?,

    @Json(name = "name")
    val name: String

) : Parcelable