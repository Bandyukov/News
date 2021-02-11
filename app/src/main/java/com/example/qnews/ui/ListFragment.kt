package com.example.qnews.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qnews.R
import com.example.qnews.databinding.FragmentListBinding
import com.example.qnews.ui.recycler.NewsAdapter
import com.example.qnews.ui.recycler.OnNewsClickListener

class ListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentListBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_list,
            container,
            false
        )

        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.lifecycleOwner = this

        val newsAdapter = NewsAdapter()
        binding.recyclerViewNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        NewsAdapter.setOnNewsClickListener(object : OnNewsClickListener {
            override fun onNewsClick(position: Int) {
                val id = viewModel.listOFNews.value!![position].uniqueId
                val bundle = Bundle()
                bundle.putInt("id", id)
                findNavController().navigate(R.id.action_listFragment_to_detailFragment, bundle)
            }
        })

        viewModel.listOFNews.observe(viewLifecycleOwner) {
            if (it != null) {
                newsAdapter.addListOfNews(it)
            }
        }

        viewModel.getAllNews()

        return binding.root
    }


}