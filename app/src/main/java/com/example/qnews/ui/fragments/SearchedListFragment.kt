package com.example.qnews.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.qnews.R
import com.example.qnews.core.models.key.Key
import com.example.qnews.databinding.FragmentSearchedListBinding
import com.example.qnews.ui.recycler.adapters.NewsListScreenAdapter
import com.example.qnews.ui.base.viewBinding
import com.example.qnews.ui.recycler.listeners.OnNewsClickListener
import com.example.qnews.ui.viewModel.factories.ViewModelProviderFactory
import com.example.qnews.ui.viewModel.other.TopicViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class SearchedListFragment : DaggerFragment(R.layout.fragment_searched_list), OnNewsClickListener {

    private val binding by viewBinding { FragmentSearchedListBinding.bind(it) }

    private val adapterNews = NewsListScreenAdapter(this)

    @Inject
    lateinit var factory: ViewModelProviderFactory

    private val viewModel by viewModels<TopicViewModel> { factory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle: Bundle? = this.arguments

        binding.recyclerViewNews.adapter = adapterNews
        bundle?.let {
            val topic = it.getString(Key.TOPIC)

            binding.toolbar3.textViewSearchedTitle.text = topic

            if (topic != null) {
                if (viewModel.listOFNews.value == null) {
                    viewModel.getNewsByTopic(topic)
                }
            } else {
                Toast.makeText(requireContext(), getString(R.string.error), Toast.LENGTH_SHORT).show()
            }
        }

        binding.toolbar3.imageVIewGetBack.setOnClickListener {
            it.findNavController().navigateUp()
        }

        viewModel.listOFNews.observe(viewLifecycleOwner) {
            adapterNews.items = it
        }

    }

    override fun onNewsClick(position: Int) {
        val news = viewModel.listOFNews.value!![position]
        val bundle1 = Bundle()
        bundle1.putParcelable("news", news)
        findNavController().navigate(R.id.action_searchedListFragment_to_detailFragment, bundle1)
    }
}