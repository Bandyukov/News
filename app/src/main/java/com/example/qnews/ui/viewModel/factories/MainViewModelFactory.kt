package com.example.qnews.ui.viewModel.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.qnews.core.repo.MainRepository
import com.example.qnews.ui.viewModel.other.MainViewModel

class MainViewModelFactory(private val repository: MainRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java))
            return MainViewModel(repository) as T
        throw IllegalArgumentException("Illegal argument")
    }
}