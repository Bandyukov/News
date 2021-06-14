package com.example.qnews.core.DI

import android.app.Application
import com.example.qnews.NewsApplication
import com.example.qnews.core.DI.resources.ResourceProvider
import com.example.qnews.core.DI.viewModel.ViewModelFactoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBuilderModule::class,
        AppModule::class,
        ViewModelFactoryModule::class,
    ]
)
@Singleton
interface AppComponent : AndroidInjector<NewsApplication> {

    fun resources() : ResourceProvider

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
