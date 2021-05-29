package com.example.qnews.core.models.suggestion

import com.example.qnews.core.models.base.ListItem

data class Search(
    val suggestion: String
) : ListItem {
    override val itemId: Long
        get() = suggestion.hashCode().toLong()

    var key: Int = 0

    constructor(suggestion: String, key: Int) : this(suggestion) {
        this.key = key
    }

    override fun toString(): String = suggestion
}