package com.example.qnews.core.db.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "recent_search_table")
@Parcelize
data class SearchDB(
    @ColumnInfo(name = "search")
    val search: String
) : Parcelable {

    @PrimaryKey(autoGenerate = true)
    var key: Int = 0

    override fun toString(): String = search
}