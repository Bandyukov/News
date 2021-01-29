package com.example.qnews.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.qnews.core.MainRepository
import com.example.qnews.core.News
import com.example.qnews.core.NewsApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _listOfNews_ = MutableLiveData<List<News>>()
    val listOFNews : LiveData<List<News>> get() = _listOfNews_

    private val context = application.applicationContext

    private val repository = MainRepository(NewsApi.NewsApiService)

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    init {
        getNews()
    }

    fun getNews() {
        uiScope.launch {
            val result = repository.getNewsFromNet()

            if (result == null) {
                Log.i("res", "NULL")
            } else {
                _listOfNews_.value = result
                result.forEach {
                    Log.i("res", it.title)
                }
            }
        }

    }

}