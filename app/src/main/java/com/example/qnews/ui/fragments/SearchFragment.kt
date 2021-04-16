package com.example.qnews.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.qnews.R
import com.example.qnews.core.db.NewsDatabase
import com.example.qnews.core.models.key.Key
import com.example.qnews.databinding.FragmentSearchBinding
import com.example.qnews.ui.base.viewBinding
import com.example.qnews.ui.recycler.listeners.OnRecyclerClickListener
import com.example.qnews.ui.recycler.adapters.SuggestionAdapter
import com.example.qnews.ui.viewModel.factories.SearchViewModelFactory
import com.example.qnews.ui.viewModel.other.SearchViewModel
import kotlinx.android.synthetic.main.toolbar_type2.view.*

class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding: FragmentSearchBinding by viewBinding { FragmentSearchBinding.bind(it) }

    private val viewModel by lazy {
        val factory =
            SearchViewModelFactory(NewsDatabase.getInstance(requireContext().applicationContext).newsDao)
        ViewModelProvider(this, factory).get(SearchViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.toolbar2) {
            imageVIewGetBack.setOnClickListener {
                val imm: InputMethodManager =
                    requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(requireView().windowToken, 0)
                findNavController().navigateUp()
            }

            imageViewstartSearching.setOnClickListener {
                val topic = editTextSearchNewsAndArticles.text.toString()
                if (topic.isNotEmpty()) {
                    findAndInsert(topic)
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.field_is_empty),
                        Toast.LENGTH_SHORT
                    ).show()
                }
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

        getRecentSearches()
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
        bundle.putString(Key.TOPIC, topic)
        val imm: InputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
        findNavController().navigate(R.id.action_searchFragment_to_searchedListFragment, bundle)
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