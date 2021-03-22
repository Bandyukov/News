package com.example.qnews.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qnews.R
import com.example.qnews.databinding.FragmentListBinding
import com.example.qnews.ui.base.NewsDelegates
import com.example.qnews.ui.base.NewsListScreenAdapter
import com.example.qnews.ui.base.viewBinding
import com.example.qnews.ui.viewModel.other.MainViewModel
import com.example.qnews.ui.recycler.adapters.NewsAdapter
import com.example.qnews.ui.recycler.listeners.OnRecyclerClickListener

class ListFragment : Fragment(R.layout.fragment_list) {
    private val binding: FragmentListBinding by viewBinding { FragmentListBinding.bind(it) }

    private val newsAdapter = NewsListScreenAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.lifecycleOwner = this

//        val bundle: Bundle? = this.arguments
//
//        bundle?.let {
//            val topic = it.getString(getString(R.string.topic))
//            if (topic != null) {
//                if (viewModel.listOFNews.value == null)
//                    viewModel.getNewsByTopic(topic)
//            } else {
//                Toast.makeText(requireContext(), getString(R.string.error), Toast.LENGTH_SHORT)
//                    .show()
//            }
//        }

        binding.recyclerViewNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        NewsDelegates.setOnRecyclerClickListener(object : OnRecyclerClickListener {
            override fun onClick(position: Int) {
                val id = viewModel.listOFNews.value!![position].uniqueId
                val bundle1 = Bundle()
                bundle1.putInt("id", id)
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