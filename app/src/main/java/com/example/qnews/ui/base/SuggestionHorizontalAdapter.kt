package com.example.qnews.ui.base

import com.example.qnews.core.models.base.BaseDiffUtilItemCallback
import com.example.qnews.core.models.base.ListItem
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class SuggestionHorizontalAdapter : AsyncListDifferDelegationAdapter<ListItem>(BaseDiffUtilItemCallback()) {
    init {
        delegatesManager.addDelegate(NewsDelegates.suggestionHorizontalDelegate())
    }
}