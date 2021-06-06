package com.example.qnews.ui.viewModel.other

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.qnews.core.models.news.News
import com.example.qnews.core.repo.MainRepository
import com.example.qnews.ui.viewModel.base.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : BaseViewModel() {

    private val _news_ = MutableLiveData<News>()
    val news : LiveData<News> get() = _news_

    fun getAllNews() {
        uiScope.launch {
            repository.getNewsFromNetAndCache()
            val result = repository.getAllNewsFromDB()
            _listOfNews_.postValue(result)
        }

    }

}