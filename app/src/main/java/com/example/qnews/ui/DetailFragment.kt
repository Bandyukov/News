package com.example.qnews.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.qnews.R
import com.example.qnews.databinding.FragmentDetailBinding

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

        binding.lifecycleOwner = this

        val bundle: Bundle? = this.arguments

        bundle?.let { bun ->
            val id = bun.getInt("id")
            viewModel.getNews(id)

            viewModel.news.observe(viewLifecycleOwner) {
                Glide.with(binding.imageViewNewsPoster.context).load(it.urlToImage).into(binding.imageViewNewsPoster)
                binding.textViewNewsTitle.text = it.title
                binding.textViewDescription.text = it.description
                binding.textViewSourceAndDate.text = String.format(
                    getString(R.string.source_date),
                    it.name,
                    it.publishedAt
                )
                binding.textViewAuthor.text = it.author
                binding.textViewContent.text = it.content
                binding.textViewUrlToSource.text = it.url

            }
        }


        return binding.root
    }

}