package com.example.qnews.core.models.suggestion

import com.example.qnews.core.models.base.ListItem

data class SuggestionHorizontalItem(
    val title: String,
    val suggestions: List<ListItem>
) : ListItem {
    override val itemId: Long
        get() = suggestions.hashCode().toLong()
}
