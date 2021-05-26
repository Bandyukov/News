package com.example.qnews.ui.viewModel.other

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.qnews.core.repo.MainRepository
import com.example.qnews.core.models.news.News
import com.example.qnews.core.NewsApi
import com.example.qnews.core.db.NewsDatabase
import com.example.qnews.ui.viewModel.base.BaseViewModel
import kotlinx.coroutines.*

class MainViewModel(private val repository: MainRepository) : BaseViewModel() {

    private val _news_ = MutableLiveData<News>()
    val news : LiveData<News> get() = _news_



    init {

    }

    fun getAllNews() {
        uiScope.launch {
            repository.getNewsFromNetAndCache()
            val result = repository.getAllNewsFromDB()
            _listOfNews_.postValue(result)

            if (listOFNews.value != null)
                Log.i("asd", "we get respond and set to view model. size is ${listOFNews.value!!.size}")
            else
                Log.i("asd", "NUll")

        }

    }

    fun getNews(id: Int) {
        uiScope.launch {
            val thisNews = repository.getCurrentNewsFromDB(id)
            _news_.postValue(thisNews)
        }
    }



    fun getDB() {
        uiScope.launch {
            _listOfNews_.postValue(repository.getAllNewsFromDB())
            if (listOFNews.value != null)
                Log.i("asd", "we get respond and set to view model. size is ${listOFNews.value!!.size}!!!!!")
            else
                Log.i("asd", "NUll")
        }
    }
}