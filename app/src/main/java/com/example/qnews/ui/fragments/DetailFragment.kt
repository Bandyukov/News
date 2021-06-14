package com.example.qnews.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.example.qnews.R
import com.example.qnews.core.mapping.DateConverter
import com.example.qnews.core.models.news.News
import com.example.qnews.databinding.FragmentDetailBinding
import com.example.qnews.ui.base.viewBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class DetailFragment : DaggerFragment(R.layout.fragment_detail) {

    private val binding: FragmentDetailBinding by viewBinding { FragmentDetailBinding.bind(it) }

    @Inject
    lateinit var requestOptions: RequestOptions

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.toolbar3) {
            imageVIewGetBack.setOnClickListener {
                it.findNavController().navigateUp()
            }

            textViewSearchedTitle.visibility = View.GONE
        }

        binding.lifecycleOwner = this

        val bundle: Bundle? = this.arguments

        bundle?.let { bun ->
            val news = bun.getParcelable<News>("news")

            if (news != null)
                with(binding) {
                    Glide.with(imageViewNewsPoster.context)
                        .applyDefaultRequestOptions(requestOptions)
                        .load(news.urlToImage)
                        .transition(withCrossFade())
                        .into(imageViewNewsPoster)

                    textViewNewsTitle.text = news.title
                    textViewDescription.text = news.description
                    textViewSource.text = news.name
                    textViewDate.text = DateConverter.convert(news.publishedAt)
                    textViewAuthor.text = news.author
                    textViewContent.text = news.content
                    textViewUrlToSource.text = news.url

                    executePendingBindings()
                }
        }
    }
}