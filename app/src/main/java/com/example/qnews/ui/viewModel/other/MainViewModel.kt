package com.example.qnews.ui.viewModel.other

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.qnews.core.repo.MainRepository
import com.example.qnews.core.models.news.News
import com.example.qnews.core.NewsApi
import com.example.qnews.core.db.NewsDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _listOfNews_ = MutableLiveData<List<News>>()
    val listOFNews: LiveData<List<News>> get() = _listOfNews_

    private val _news_ = MutableLiveData<News>()
    val news : LiveData<News> get() = _news_

    private val context = application.applicationContext

    private val database = NewsDatabase.getInstance(context)
    private val repository = MainRepository(NewsApi.NewsApiService, database.newsDao)

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    init {

    }

    fun getAllNews() {
        uiScope.launch {
            repository.getNewsFromNet()
            val result = repository.getAllNewsFromDB()
            _listOfNews_.value = result

            if (listOFNews.value != null)
                Log.i("asd", "we get respond and set to view model. size is ${listOFNews.value!!.size}")
            else
                Log.i("asd", "NUll")
        }

    }

    fun getNews(id: Int) {
        uiScope.launch {
            val thisNews = repository.getCurrentNewsFromDB(id)
            _news_.value = thisNews
        }
    }

    fun getNewsByTopic(topic: String) {
        uiScope.launch {
            repository.getNewsFromNetByTopic(topic)
            val result = repository.getAllNewsFromDB()
            _listOfNews_.value = result
        }
    }

    fun getDB() {
        uiScope.launch {
            _listOfNews_.value = repository.getAllNewsFromDB()
            if (listOFNews.value != null)
                Log.i("asd", "we get respond and set to view model. size is ${listOFNews.value!!.size}!!!!!")
            else
                Log.i("asd", "NUll")
        }
    }

}