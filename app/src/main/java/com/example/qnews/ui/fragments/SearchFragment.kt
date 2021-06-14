package com.example.qnews.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.qnews.R
import com.example.qnews.core.models.key.Key
import com.example.qnews.core.models.suggestion.Search
import com.example.qnews.databinding.FragmentSearchBinding
import com.example.qnews.ui.base.viewBinding
import com.example.qnews.ui.recycler.adapters.SuggestionHorizontalAdapter
import com.example.qnews.ui.recycler.listeners.OnSuggestionClickListener
import com.example.qnews.ui.viewModel.factories.ViewModelProviderFactory
import com.example.qnews.ui.viewModel.other.SearchViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class SearchFragment : DaggerFragment(R.layout.fragment_search), OnSuggestionClickListener {

    private val binding: FragmentSearchBinding by viewBinding { FragmentSearchBinding.bind(it) }

    private val adapter by lazy { SuggestionHorizontalAdapter(this) }

    @Inject
    lateinit var factory: ViewModelProviderFactory

    private val viewModel by viewModels<SearchViewModel> { factory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getRecentSearches()

        binding.recyclerView.adapter = adapter

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
                hideKeyboard()
                it.findNavController().navigateUp()
            }
        }
        viewModel.topics.observe(viewLifecycleOwner) {
            adapter.items = it
            adapter.notifyDataSetChanged()
        }

    }

    private fun find(topic: String) {
        viewModel.updateRecentSearches(topic)
        val bundle = Bundle()
        bundle.putString(Key.TOPIC, topic)
        hideKeyboard()
        findNavController().navigate(R.id.action_searchFragment_to_searchedListFragment, bundle)
    }

    private fun hideKeyboard() {
        val imm: InputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    override fun onSuggestionClick(search: Search) {
        find(search.toString())
    }


}