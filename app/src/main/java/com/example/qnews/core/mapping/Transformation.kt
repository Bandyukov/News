package com.example.qnews.core.mapping

import com.example.qnews.core.News
import com.example.qnews.core.NewsVO


fun NewsVO.toNews() : News = News(source,
    author,
    title,
    description,
    url,
    urlToImage,
    publishedAt,
    content)


