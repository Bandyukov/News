package com.example.qnews.ui.viewModel.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.qnews.core.models.news.News
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

abstract class BaseViewModel : ViewModel() {
    protected val _listOfNews_ = MutableLiveData<List<News>>()
    val listOFNews: LiveData<List<News>> get() = _listOfNews_

    private val job = Job()
    protected val uiScope = CoroutineScope(Dispatchers.IO + job)

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}