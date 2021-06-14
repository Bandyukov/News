package com.example.qnews.core.network

data class NewsParams(
    val endpoint: String,
    val country: String = String(),
    val pageSize: Int = 0,
    val q: String = String(),
) {
    companion object {
        const val apiKey = "1855f8b714864e30be7a1e57b5d8855b"
    }
}