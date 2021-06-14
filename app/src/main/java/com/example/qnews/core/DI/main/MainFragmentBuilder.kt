package com.example.qnews.core.DI.main

import com.example.qnews.ui.fragments.DetailFragment
import com.example.qnews.ui.fragments.ListFragment
import com.example.qnews.ui.fragments.SearchFragment
import com.example.qnews.ui.fragments.SearchedListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuilder {
    @ContributesAndroidInjector
    abstract fun provideDetailFragment() : DetailFragment

    @ContributesAndroidInjector
    abstract fun provideListFragment() : ListFragment

    @ContributesAndroidInjector
    abstract fun provideSearchedListFragment() : SearchedListFragment

    @ContributesAndroidInjector
    abstract fun provideSearchFragment() : SearchFragment
}