package com.example.qnews.ui.base

import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.example.qnews.core.db.entities.SearchDB
import com.example.qnews.core.mapping.DateConverter
import com.example.qnews.core.models.base.ListItem
import com.example.qnews.core.models.news.News
import com.example.qnews.core.models.suggestion.Search
import com.example.qnews.core.models.suggestion.SuggestionHorizontalItem
import com.example.qnews.databinding.NewsItemBinding
import com.example.qnews.databinding.SuggestionHorizontalItemBinding
import com.example.qnews.databinding.SuggestionItemBinding
import com.example.qnews.ui.recycler.listeners.OnRecyclerClickListener
import com.example.qnews.ui.recycler.listeners.OnSuggestionClickListener
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import kotlinx.android.synthetic.main.suggestion_item.view.*

object NewsDelegates {

    private lateinit var onRecyclerClickListener: OnRecyclerClickListener
    fun setOnRecyclerClickListener(onRecyclerClickListener: OnRecyclerClickListener) {
        this.onRecyclerClickListener = onRecyclerClickListener
    }

    private lateinit var onSuggestionClickListener: OnSuggestionClickListener
    fun setOnSuggestionClickListener(onSuggestionClickListener: OnSuggestionClickListener) {
        this.onSuggestionClickListener = onSuggestionClickListener
    }

    val newsListDelegate = adapterDelegateViewBinding<News, ListItem, NewsItemBinding>(
        { inflater, container ->
            NewsItemBinding.inflate(inflater, container, false)
        }
    ) {
        bind {
            with(binding) {
                textViewTitle.text = item.title
                textViewDate.text = DateConverter.convert(item.publishedAt)
                Glide.with(imageViewPoster.context)
                    .load(item.urlToImage)
                    .transition(withCrossFade())
                    .into(imageViewPoster)
                executePendingBindings()

                textViewTitle.setOnClickListener {
                    onRecyclerClickListener.onClick(adapterPosition)
                }
            }
        }
    }

    fun suggestionHorizontalDelegate() = adapterDelegateViewBinding<
            SuggestionHorizontalItem,
            ListItem,
            SuggestionHorizontalItemBinding>(
        { inflater, container ->
            SuggestionHorizontalItemBinding.inflate(inflater, container, false)
        }
    ) {
        val adapter = SuggestionVerticalAdapter()
        binding.suggestionHorizontalRecyclerView.adapter = adapter

        bind {
            binding.titleSuggestionTextView.text = item.title
            adapter.items = item.suggestions
        }
    }

    fun suggestionVerticalDelegate() =
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