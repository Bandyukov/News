package com.example.qnews.ui.fragments

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.qnews.R
import com.example.qnews.core.models.key.Key
import com.example.qnews.databinding.FragmentSearchedListBinding
import com.example.qnews.ui.base.NewsListScreenAdapter
import com.example.qnews.ui.base.viewBinding
import com.example.qnews.ui.viewModel.other.MainViewModel


class SearchedListFragment : Fragment(R.layout.fragment_searched_list) {

    private val binding by viewBinding { FragmentSearchedListBinding.bind(it) }

    private val adapterNews = NewsListScreenAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle: Bundle? = this.arguments
        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.recyclerViewNews.adapter = adapterNews
        bundle?.let {
            val topic = it.getString(Key.TOPIC)
            if (topic != null) {
                if (viewModel.listOFNews.value == null) {
                    Log.i("asd", "try to load $topic")
                    viewModel.getNewsByTopic(topic)
                    Log.i("asd", "managed to load")
                }
            } else {
                Toast.makeText(requireContext(), getString(R.string.error), Toast.LENGTH_SHORT).show()
            }
            //viewModel.getDB()
        }



        viewModel.listOFNews.observe(viewLifecycleOwner) {
            Log.i("asd", "try to set items")
            adapterNews.items = it
            Log.i("asd", "managed to set items")
        }

    }


}