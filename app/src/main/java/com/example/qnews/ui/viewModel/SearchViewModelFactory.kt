package com.example.qnews.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.qnews.core.db.NewsDAO
import com.example.qnews.ui.viewModel.other.SearchViewModel
import java.lang.IllegalArgumentException

class SearchViewModelFactory(val dao: NewsDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(dao) as T
        }
        throw IllegalArgumentException("EXCEPTION")
    }
}