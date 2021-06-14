package com.example.qnews

import com.example.qnews.core.DI.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class NewsApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }
}