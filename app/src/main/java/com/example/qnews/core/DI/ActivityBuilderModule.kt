package com.example.qnews.core.DI

import com.example.qnews.MainActivity
import com.example.qnews.SplashActivity
import com.example.qnews.core.DI.main.MainFragmentBuilder
import com.example.qnews.core.DI.main.MainModule
import com.example.qnews.core.DI.main.MainScope
import com.example.qnews.core.DI.main.MainViewModelModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeSplashActivity() : SplashActivity

    @ContributesAndroidInjector(modules = [
        MainModule::class,
        MainFragmentBuilder::class,
        MainViewModelModule::class,
    ])
    @MainScope
    abstract fun contributeMainActivity() : MainActivity
}