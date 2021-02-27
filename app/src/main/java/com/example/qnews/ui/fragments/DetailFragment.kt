package com.example.qnews.ui.fragments

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

                val ch: CharSequence = it.author as CharSequence
                binding.chip.text = ch

                val a = DrawableBuilder()
                    .rectangle()
                    .rounded()
                    .solidColor(resources.getColor(R.color.dark_red))
                    .solidColorPressed(resources.getColor(R.color.white))
                    .build()
                //Log.i("qwe", a.toString())
                //Glide.with(binding.myImageView.context).load(a).into(binding.myImageView)

                binding.executePendingBindings()
            }
        }

        return binding.root
    }


}