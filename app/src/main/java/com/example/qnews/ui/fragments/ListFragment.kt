package com.example.qnews.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qnews.R
import com.example.qnews.databinding.FragmentListBinding
import com.example.qnews.ui.base.viewBinding
import com.example.qnews.ui.recycler.adapters.NewsListScreenAdapter
import com.example.qnews.ui.recycler.listeners.OnNewsClickListener
import com.example.qnews.ui.viewModel.factories.ViewModelProviderFactory
import com.example.qnews.ui.viewModel.other.MainViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ListFragment : DaggerFragment(R.layout.fragment_list), OnNewsClickListener {

    private val binding: FragmentListBinding by viewBinding { FragmentListBinding.bind(it) }

    private val newsAdapter = NewsListScreenAdapter(this)

    @Inject
    lateinit var factory: ViewModelProviderFactory

    val viewModel by viewModels<MainViewModel> { factory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this

        binding.recyclerViewNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        with(binding) {
            toolbar1.imageViewLoop.setOnClickListener {
                findNavController().navigate(R.id.action_listFragment_to_searchFragment)
            }

            refreshLayout.setOnRefreshListener {
                refreshLayout.isRefreshing = true
                viewModel.getAllNews()
                refreshLayout.isRefreshing = false
            }
        }

        viewModel.listOFNews.observe(viewLifecycleOwner) {
            if (it != null) {
                newsAdapter.items = it
                binding.refreshLayout.isRefreshing = false
            } else {
                binding.refreshLayout.isRefreshing = true
            }
        }

        if (viewModel.listOFNews.value == null) {
            //if (bundle == null)
                viewModel.getAllNews()
        }


    }

    override fun onNewsClick(position: Int) {
        val news = viewModel.listOFNews.value!![position]
        val bundle1 = Bundle()
        bundle1.putParcelable("news", news)
        findNavController().navigate(R.id.action_listFragment_to_detailFragment, bundle1)
    }
}