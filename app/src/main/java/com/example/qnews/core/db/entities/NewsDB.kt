package com.example.qnews.core.db.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "the_news_table")
data class NewsDB(

    @ColumnInfo(name = "id")
    val id: String?,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "author")
    val author: String?,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String?,

    @ColumnInfo(name = "url")
    val url: String,

    @ColumnInfo(name = "urlToImage")
    val urlToImage: String?,

    @ColumnInfo(name = "publishedAt")
    val publishedAt: String,

    @ColumnInfo(name = "content")
    val content: String?
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var uniqueId: Int = 0

    constructor(
        id: String?, name: String, author: String?, title: String, description: String?, url: String, urlToImage: String?, publishedAt: String,
        content: String?, uniqueId: Int
    ) : this(id, name , author, title, description, url, urlToImage, publishedAt, content) {
        this.uniqueId = uniqueId
    }
}