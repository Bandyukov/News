package com.example.qnews.core.DI.resources

import android.app.Application
import javax.inject.Inject

class AndroidResourceProvider @Inject constructor(private val application: Application) :
    ResourceProvider {
    override fun string(id: Int): String = application.resources.getString(id)
    override fun array(id: Int): Array<String> = application.resources.getStringArray(id)
}