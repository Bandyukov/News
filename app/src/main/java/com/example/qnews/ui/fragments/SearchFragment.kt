package com.example.qnews.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.qnews.R
import com.example.qnews.core.db.NewsDatabase
import com.example.qnews.databinding.FragmentSearchBinding
import com.example.qnews.ui.recycler.listeners.OnRecyclerClickListener
import com.example.qnews.ui.recycler.adapters.SuggestionAdapter
import com.example.qnews.ui.viewModel.SearchViewModelFactory
import com.example.qnews.ui.viewModel.other.SearchViewModel

class SearchFragment : Fragment() {

    private lateinit var binding:FragmentSearchBinding

    private val viewModel by lazy {
        val factory = SearchViewModelFactory(NewsDatabase.getInstance(requireContext().applicationContext).newsDao)
        ViewModelProvider(this, factory).get(SearchViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_search,
            container,
            false
        )

        binding.imageViewLoop.setOnClickListener {
            val topic = binding.editTextSearch.text.toString()
            if (topic.isNotEmpty()) {
                findAndInsert(topic)
            } else {
                Toast.makeText(requireContext(), getString(R.string.field_is_empty), Toast.LENGTH_SHORT).show()
            }
        }

        val suggestionAdapterHeadlines = SuggestionAdapter()
        val suggestionAdapterWorld = SuggestionAdapter()
        val recentSearchesAdapter = SuggestionAdapter()


        setRecyclerProperties(binding.suggestionsHeadlinesRecyclerView, suggestionAdapterHeadlines)
        setRecyclerProperties(binding.suggestionsWorldRecyclerView, suggestionAdapterWorld)
        setRecyclerProperties(binding.recentSearchesRecyclerView, recentSearchesAdapter)

        suggestionAdapterHeadlines.setOnNewClickListener(object : OnRecyclerClickListener {
            override fun onClick(position: Int) {
                val topic = viewModel.suggestionsHeadlines.value!![position]
                findAndInsert(topic)
            }
        })

        suggestionAdapterWorld.setOnNewClickListener(object : OnRecyclerClickListener {
            override fun onClick(position: Int) {
                val topic = viewModel.suggestionsWorld.value!![position]
                findAndInsert(topic)
            }
        })

        recentSearchesAdapter.setOnNewClickListener(object : OnRecyclerClickListener {
            override fun onClick(position: Int) {
                val topic = viewModel.userSearches.value!![position]
                find(topic)
            }
        })

        binding.clearButton.setOnClickListener {
            clearSearches()
        }

        viewModel.suggestionsHeadlines.observe(viewLifecycleOwner) {
            suggestionAdapterHeadlines.setSuggestions(it)
        }

        viewModel.suggestionsWorld.observe(viewLifecycleOwner) {
            suggestionAdapterWorld.setSuggestions(it)
        }

        viewModel.userSearches.observe(viewLifecycleOwner) {
            recentSearchesAdapter.setSuggestions(it)
            binding.clearButton.visibility = if (it.isNotEmpty())
                View.VISIBLE
            else
                View.INVISIBLE
        }

        return binding.root
    }

    private fun getRecentSearches() {
        viewModel.getAllRecentSearches()
    }

    private fun insertSearch(topic: String) {
        viewModel.insertRecentSearch(topic)
    }

    private fun clearSearches() {
        viewModel.clearRecentSearches()
    }

    private fun find(topic: String) {
        val bundle = Bundle()
        bundle.putString("topic", topic)
        val imm: InputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
        findNavController().navigate(R.id.action_searchFragment_to_listFragment, bundle)
    }

    private fun setRecyclerProperties(recyclerView: RecyclerView, listAdapter: SuggestionAdapter) {
        recyclerView.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(
                requireContext().applicationContext,
                RecyclerView.HORIZONTAL,
                false
            )
        }
    }

    private fun findAndInsert(topic: String) {
        find(topic)
        insertSearch(topic)
    }


}