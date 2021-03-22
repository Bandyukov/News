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
import com.example.qnews.databinding.FragmentDetailBinding
import com.example.qnews.ui.viewModel.other.MainViewModel
import top.defaults.drawabletoolbox.DrawableBuilder

class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentDetailBinding>(
            inflater,
            R.layout.fragment_detail,
            container,
            false
        )

        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        with(binding.toolbar3) {
            imageVIewGetBack.setOnClickListener {
                it.findNavController().navigateUp()
            }

            textViewSearchedTitle.visibility = View.GONE
        }

        binding.lifecycleOwner = this

        val bundle: Bundle? = this.arguments

        bundle?.let { bun ->
            val id = bun.getInt("id")
            viewModel.getNews(id)

            viewModel.news.observe(viewLifecycleOwner) {
                with(binding) {
                    Glide.with(imageViewNewsPoster.context)
                        .load(it.urlToImage)
                        .transition(withCrossFade())
                        .into(imageViewNewsPoster)
                    textViewNewsTitle.text = it.title
                    textViewDescription.text = it.description
                    textViewSourceAndDate.text = String.format(
                        getString(R.string.source_date),
                        it.name,
                        it.publishedAt
                    )
                    textViewAuthor.text = it.author
                    textViewContent.text = it.content
                    textViewUrlToSource.text = it.url

                }

                binding.executePendingBindings()
            }
        }


        return binding.root
    }


}