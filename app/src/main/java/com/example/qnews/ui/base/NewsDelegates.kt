package com.example.qnews.ui.base

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.example.qnews.R
import com.example.qnews.core.mapping.DateConverter
import com.example.qnews.core.models.base.ListItem
import com.example.qnews.core.models.news.News
import com.example.qnews.core.models.suggestion.Search
import com.example.qnews.core.models.suggestion.SuggestionHorizontalItem
import com.example.qnews.databinding.NewsItemBinding
import com.example.qnews.databinding.SuggestionHorizontalItemBinding
import com.example.qnews.databinding.SuggestionItemBinding
import com.example.qnews.ui.recycler.adapters.SuggestionVerticalAdapter
import com.example.qnews.ui.recycler.listeners.OnNewsClickListener
import com.example.qnews.ui.recycler.listeners.OnSuggestionClickListener
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

object NewsDelegates {

    fun newsListDelegate(onNewsClickListener: OnNewsClickListener) =
        adapterDelegateViewBinding<News, ListItem, NewsItemBinding>(
            { inflater, container ->
                NewsItemBinding.inflate(inflater, container, false)
            }
        ) {
            bind {
                with(binding) {
                    textViewTitle.text = item.title
                    textViewDate.text = DateConverter.convert(item.publishedAt)
                    val requestOptions = RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.connection_error_image)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                    Glide.with(imageViewPoster.context)
                        .applyDefaultRequestOptions(requestOptions)
                        .load(item.urlToImage)
                        .transition(withCrossFade())
                        .into(imageViewPoster)

                    executePendingBindings()

                    textViewTitle.setOnClickListener {
                        onNewsClickListener.onNewsClick(adapterPosition)
                    }
                }
            }
        }

    fun suggestionHorizontalDelegate(onSuggestionClickListener: OnSuggestionClickListener) =
        adapterDelegateViewBinding<
                SuggestionHorizontalItem,
                ListItem,
                SuggestionHorizontalItemBinding>(
            { inflater, container ->
                SuggestionHorizontalItemBinding.inflate(inflater, container, false)
            }
        ) {
            val adapter = SuggestionVerticalAdapter(onSuggestionClickListener)
            binding.suggestionHorizontalRecyclerView.adapter = adapter

            bind {
                binding.titleSuggestionTextView.text = item.title
                adapter.items = item.suggestions
            }
        }

    fun suggestionVerticalDelegate(onSuggestionClickListener: OnSuggestionClickListener) =
        adapterDelegateViewBinding<Search, ListItem, SuggestionItemBinding>(
            { inflater, container ->
                SuggestionItemBinding.inflate(inflater, container, false)
            }
        ) {
            bind {
                binding.suggestionTextView.text = item.suggestion

                binding.suggestionTextView.setOnClickListener {
                    onSuggestionClickListener.onSuggestionClick(item)
                }
            }
        }
}