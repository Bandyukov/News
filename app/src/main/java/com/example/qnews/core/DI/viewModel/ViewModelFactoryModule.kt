package com.example.qnews.core.DI.viewModel

import androidx.lifecycle.ViewModelProvider
import com.example.qnews.ui.viewModel.factories.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelProviderFactory: ViewModelProviderFactory) :
            ViewModelProvider.Factory

}