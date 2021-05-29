package com.example.qnews.ui.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.qnews.R
import com.example.qnews.core.NewsApi
import com.example.qnews.core.NewsApiService
import com.example.qnews.core.db.NewsDatabase
import com.example.qnews.core.mapping.toSearches
import com.example.qnews.core.models.base.ListItem
import com.example.qnews.core.models.key.Key
import com.example.qnews.core.models.suggestion.Search
import com.example.qnews.core.models.suggestion.SuggestionHorizontalItem
import com.example.qnews.core.repo.MainRepository
import com.example.qnews.databinding.FragmentSearchBinding
import com.example.qnews.ui.base.NewsDelegates
import com.example.qnews.ui.base.SuggestionHorizontalAdapter
import com.example.qnews.ui.base.viewBinding
import com.example.qnews.ui.recycler.listeners.OnSuggestionClickListener
import com.example.qnews.ui.viewModel.factories.SearchViewModelFactory
import com.example.qnews.ui.viewModel.other.SearchViewModel
import com.google.android.material.snackbar.Snackbar

class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding: FragmentSearchBinding by viewBinding { FragmentSearchBinding.bind(it) }

    private val adapter by lazy { SuggestionHorizontalAdapter() }

    private val viewModel by lazy {
        val dao = NewsDatabase.getInstance(requireContext().applicationContext).newsDao
        val repository = MainRepository(NewsApi.NewsApiService, dao)
        val factory = SearchViewModelFactory(repository)
        ViewModelProvider(this, factory).get(SearchViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getRecentSearches()

        binding.recyclerView.adapter = adapter

        val headlines = resources.getStringArray(R.array.headlines)
        val world = resources.getStringArray(R.array.world)

        with(binding.toolbar2) {

            imageViewstartSearching.setOnClickListener {
                val topic = binding.toolbar2.editTextSeaechNewsAndAtricles.text.toString()
                if (topic.isNotEmpty()) {
                    find(topic)
                } else {
                    Snackbar.make(
                        binding.root,
                        getString(R.string.field_is_empty),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }

            imageVIewGetBack.setOnClickListener {
                it.findNavController().navigateUp()
            }
        }
        viewModel.latestSearches.observe(viewLifecycleOwner) {

            val suggestions = mutableListOf<ListItem>(
                SuggestionHorizontalItem(getString(R.string.headlines), headlines.toSearches()),
                SuggestionHorizontalItem(getString(R.string.world), world.toSearches())
            )

            if (it.isNotEmpty())
                suggestions.add(SuggestionHorizontalItem(getString(R.string.recent_searches), it))

            adapter.items = suggestions
        }

        NewsDelegates.setOnSuggestionClickListener(object : OnSuggestionClickListener {
            override fun onSuggestionClick(search: Search) {
                find(search.toString())
            }
        })
    }

    private fun find(topic: String) {
        viewModel.updateRecentSearches(topic)
        val bundle = Bundle()
        bundle.putString(Key.TOPIC, topic)
        val imm: InputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
        findNavController().navigate(R.id.action_searchFragment_to_searchedListFragment, bundle)
        binding.toolbar2.editTextSeaechNewsAndAtricles.setText(topic)
    }

}