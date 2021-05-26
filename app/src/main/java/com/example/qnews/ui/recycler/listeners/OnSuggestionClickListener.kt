package com.example.qnews.ui.recycler.listeners

import com.example.qnews.core.models.suggestion.Search

interface OnSuggestionClickListener {
    fun onSuggestionClick(search: Search)
}