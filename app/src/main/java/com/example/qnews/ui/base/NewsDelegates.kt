package com.example.qnews.ui.base

import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.R
import com.example.qnews.core.models.base.ListItem
import com.example.qnews.core.models.news.News
import com.example.qnews.databinding.NewsItemBinding
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

object NewsDelegates {
    val newsListDelegate = adapterDelegateViewBinding<News, ListItem, NewsItemBinding>(
        { inflater, container ->
            NewsItemBinding.inflate(inflater, container, false)
        }
    ) {
        bind {
            with(binding) {
                textViewTitle.text = item.title
                textViewDate.text = item.publishedAt
                Glide.with(imageViewPoster.context).load(item.urlToImage).into(imageViewPoster)
                executePendingBindings()
                textViewTitle.setOnClickListener {

                }
            }
        }


    }
}