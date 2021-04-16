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
import com.example.qnews.ui.base.NewsDelegates
import com.example.qnews.ui.base.NewsListScreenAdapter
import com.example.qnews.ui.base.viewBinding
import com.example.qnews.ui.viewModel.other.MainViewModel
import com.example.qnews.ui.recycler.listeners.OnRecyclerClickListener
import com.example.qnews.ui.viewModel.factories.MainViewModelFactory

class ListFragment : Fragment(R.layout.fragment_list) {
    private val binding: FragmentListBinding by viewBinding { FragmentListBinding.bind(it) }

    private val newsAdapter = NewsListScreenAdapter()

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

        NewsDelegates.setOnRecyclerClickListener(object : OnRecyclerClickListener {
            override fun onClick(position: Int) {
                val news = viewModel.listOFNews.value!![position]
                val bundle1 = Bundle()
                bundle1.putParcelable("news", news)
                findNavController().navigate(R.id.action_listFragment_to_detailFragment, bundle1)
            }
        })

        binding.toolbar1.imageViewLoop.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_searchFragment)
        }

        viewModel.listOFNews.observe(viewLifecycleOwner) {
            if (it != null) {
                newsAdapter.items = it
            }
        }

        if (viewModel.listOFNews.value == null) {
            //if (bundle == null)
                viewModel.getAllNews()
        }
    }


}