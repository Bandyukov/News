package com.example.qnews.core.models.suggestion

import com.example.qnews.core.models.base.ListItem

data class Search(
    val suggestion: String
) : ListItem {
    override val itemId: Long
        get() = suggestion.hashCode().toLong()

    override fun toString(): String = suggestion
}