package com.example.qnews.core.DI.main

import androidx.lifecycle.ViewModel
import com.example.qnews.core.DI.viewModel.ViewModelKey
import com.example.qnews.ui.viewModel.other.MainViewModel
import com.example.qnews.ui.viewModel.other.SearchViewModel
import com.example.qnews.ui.viewModel.other.TopicViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TopicViewModel::class)
    abstract fun bindTopicViewModel(topicViewModel: TopicViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(searchViewModel: SearchViewModel) : ViewModel
}