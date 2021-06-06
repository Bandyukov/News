package com.example.qnews.ui.recycler.adapters

import com.example.qnews.core.models.base.BaseDiffUtilItemCallback
import com.example.qnews.core.models.base.ListItem
import com.example.qnews.ui.base.NewsDelegates
import com.example.qnews.ui.recycler.listeners.OnSuggestionClickListener
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class SuggestionVerticalAdapter(onSuggestionClickListener: OnSuggestionClickListener) :
    AsyncListDifferDelegationAdapter<ListItem>(BaseDiffUtilItemCallback()) {
    init {
        delegatesManager.addDelegate(NewsDelegates.suggestionVerticalDelegate(onSuggestionClickListener))
    }
}