package com.example.qnews.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.example.qnews.R
import com.example.qnews.core.mapping.DateConverter
import com.example.qnews.core.models.news.News
import com.example.qnews.databinding.FragmentDetailBinding
import com.example.qnews.ui.base.viewBinding

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val binding: FragmentDetailBinding by viewBinding { FragmentDetailBinding.bind(it) }

//    private val viewModel by lazy {
//        val dao = NewsDatabase.getInstance(requireContext().applicationContext).newsDao
//        val repo = MainRepository(NewsApi.NewsApiService, dao)
//        val factory = MainViewModelFactory(repo)
//        ViewModelProvider(this, factory).get(MainViewModel::class.java)
//    }


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