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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.qnews.R
import com.example.qnews.databinding.FragmentSearchBinding
import com.example.qnews.ui.recycler.listeners.OnRecyclerClickListener
import com.example.qnews.ui.recycler.adapters.SuggestionAdapter

class SearchFragment : Fragment() {

    private lateinit var binding:FragmentSearchBinding

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

        val suggestionsHeadlines = listOf(
            "Covid19",
            "Coronavirus",
            "Cryptocurrency",
            "Climate",
            "BLM",
            "LGBT",
            "Games"
        )
        val suggestionsWorld = listOf(
            "Europe",
            "USA",
            "Russia",
            "China",
            "Asia",
            "Australia"
        )

        binding.imageViewLoop.setOnClickListener {
            val topic = binding.editTextSearch.text.toString()
            if (topic.isNotEmpty()) {
                find(topic)
            } else {
                Toast.makeText(requireContext(), getString(R.string.field_is_empty), Toast.LENGTH_SHORT).show()
            }
        }

        val suggestionAdapterHeadlines = SuggestionAdapter()
        val suggestionAdapterWorld = SuggestionAdapter()


        setRecyclerProperties(binding.suggestionsHeadlinesRecyclerView, suggestionAdapterHeadlines)
        setRecyclerProperties(binding.suggestionsWorldRecyclerView, suggestionAdapterWorld)

        suggestionAdapterHeadlines.setOnNewClickListener(object : OnRecyclerClickListener {
            override fun onClick(position: Int) {
                val topic = suggestionsHeadlines[position]
                find(topic)
            }
        })

        suggestionAdapterWorld.setOnNewClickListener(object : OnRecyclerClickListener {
            override fun onClick(position: Int) {
                val topic = suggestionsWorld[position]
                find(topic)
            }
        })

        suggestionAdapterHeadlines.setSuggestions(suggestionsHeadlines)
        suggestionAdapterWorld.setSuggestions(suggestionsWorld)

        return binding.root
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



}