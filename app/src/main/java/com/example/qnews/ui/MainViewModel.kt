package com.example.qnews.ui

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.qnews.core.repo.MainRepository
import com.example.qnews.core.models.News
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
            //Toast.makeText(context, "GOT", Toast.LENGTH_SHORT).show()
            _listOfNews_.value = result
            result.map {
                Log.i("res", it.title)
            }

        }

    }

    fun getNews(id: Int) {
        uiScope.launch {
            val thisNews = repository.getCurrentNewsFromDB(id)
            _news_.value = thisNews
        }
    }

}