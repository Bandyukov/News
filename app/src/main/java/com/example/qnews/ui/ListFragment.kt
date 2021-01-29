package com.example.qnews.ui

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qnews.R
import com.example.qnews.databinding.FragmentListBinding

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




        viewModel.listOFNews.observe(viewLifecycleOwner) {
            if (it != null) {
                Toast.makeText(requireContext(), "GOT", Toast.LENGTH_SHORT).show()
                newsAdapter.addListOfNews(it)
            }
        }

        return binding.root
    }


}