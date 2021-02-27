package com.example.qnews.core.mapping

import com.example.qnews.core.models.News
import com.example.qnews.core.db.entities.NewsDB
import com.example.qnews.core.db.entities.SearchDB
import com.example.qnews.core.models.NewsVO


fun NewsVO.toNews(): News =
    News(source.id, source.name, author, title, description, url, urlToImage, publishedAt, content)

fun NewsVO.toNewsDB(): NewsDB =
    NewsDB(source.id,source.name, author, title, description, url, urlToImage, publishedAt, content)

fun NewsDB.toNews(): News =
    News(id, name, author, title, description, url, urlToImage, publishedAt, content, uniqueId)

fun News.toNewsDB(): NewsDB =
    NewsDB(id, name, author, title, description, url, urlToImage, publishedAt, content, uniqueId)


