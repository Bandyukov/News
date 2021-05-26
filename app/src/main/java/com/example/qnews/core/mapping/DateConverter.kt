package com.example.qnews.core.mapping

import android.annotation.SuppressLint
import android.util.Log
import com.example.qnews.core.models.key.Key
import java.text.SimpleDateFormat
import java.util.*

object DateConverter {
    @SuppressLint("ConstantLocale")
    private val sdf1 = SimpleDateFormat(Key.DATE_FORMAT_STD, Locale.getDefault())
    @SuppressLint("ConstantLocale")
    private val sdf2 = SimpleDateFormat(Key.DATE_FORMAT_PRESENTABLE, Locale.getDefault())

    init { sdf1.timeZone = TimeZone.getTimeZone("UTC") }

    private fun parse(date: String) : Date? = sdf1.parse(date)

    private fun format(date: Date) : String = sdf2.format(date)

    fun convert(s: String) : String {
        val date = parse(s)
        date?.let { return format(it) }
        return String()
    }
}