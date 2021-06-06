package com.example.qnews.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qnews.R
import com.example.qnews.core.NewsApi
import com.example.qnews.core.db.NewsDatabase
import com.example.qnews.core.repo.MainRepository
import com.example.qnews.databinding.FragmentListBinding
import com.example.qnews.ui.recycler.adapters.NewsListScreenAdapter
import com.example.qnews.ui.base.viewBinding
import com.example.qnews.ui.recycler.listeners.OnNewsClickListener
import com.example.qnews.ui.viewModel.other.MainViewModel
import com.example.qnews.ui.viewModel.factories.MainViewModelFactory

class ListFragment : Fragment(R.layout.fragment_list), OnNewsClickListener {
    private val binding: FragmentListBinding by viewBinding { FragmentListBinding.bind(it) }

    private val newsAdapter = NewsListScreenAdapter(this)

    private val viewModel by lazy {
        val dao = NewsDatabase.getInstance(requireContext().applicationContext).newsDao
        val repo = MainRepository(NewsApi.NewsApiService, dao)
        val factory = MainViewModelFactory(repo)
        ViewModelProvider(this, factory).get(MainViewModel::class.java)
    }

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