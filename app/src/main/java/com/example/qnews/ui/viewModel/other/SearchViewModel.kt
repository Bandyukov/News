package com.example.qnews.ui.viewModel.other

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.qnews.core.db.NewsDAO
import com.example.qnews.core.db.entities.SearchDB
import com.example.qnews.ui.viewModel.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(private val dao: NewsDAO) : BaseViewModel() {

    private val _suggestionsHeadlines = MutableLiveData<List<String>>()
    val suggestionsHeadlines: LiveData<List<String>> get() = _suggestionsHeadlines

    private val _suggestionsWorld = MutableLiveData<List<String>>()
    val suggestionsWorld: LiveData<List<String>> get() = _suggestionsWorld

    private val _userSearches = MutableLiveData<List<String>>()
    val userSearches: LiveData<List<String>> get() = _userSearches

    init {

        _suggestionsHeadlines.postValue(
            listOf(
                "Covid19",
                "Coronavirus",
                "Cryptocurrency",
                "Climate",
                "BLM",
                "LGBT",
                "Games"
            )
        )

        _suggestionsWorld.postValue(
            listOf(
                "Europe",
                "USA",
                "Russia",
                "China",
                "Asia",
                "Australia"
            )
        )

        getAllRecentSearches()
    }


    fun getAllRecentSearches() {
        viewModelScope.launch(Dispatchers.IO) {
            _userSearches.postValue(dao.getAllRecentSearch().map { it.toString() })
        }
    }

    fun insertRecentSearch(search: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.insertSearch(SearchDB(search))
        }
    }

    fun clearRecentSearches() {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteAllRecentSearch()
            getAllRecentSearches()
        }
    }

}