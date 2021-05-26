package com.example.qnews.ui.viewModel.other

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qnews.core.models.suggestion.Search
import com.example.qnews.core.repo.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: MainRepository) : ViewModel() {

    private val _latestSearches = MutableLiveData<List<Search>>()
    val latestSearches : LiveData<List<Search>> get() = _latestSearches

    init {
        getRecentSearches()
    }

    private fun getRecentSearches() {
        viewModelScope.launch(Dispatchers.IO) {
            _latestSearches.postValue(repository.getAllRecentSearches())
        }
    }

    fun updateRecentSearches(topic: String) {
        viewModelScope.launch(Dispatchers.IO) {
            var flag = false

            latestSearches.value?.let { ls ->
                for (search in ls) {
                    if (search.suggestion == topic) {
                        flag = true
                        repository.deleteSearch(search)
                    }
                }
            }
            repository.updateSearches(Search(topic))
        }
    }
}