package com.example.qnews.ui

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
import com.example.qnews.R
import com.example.qnews.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding:FragmentSearchBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_search,
            container,
            false
        )

        binding.imageViewLoop.setOnClickListener {
            val topic = binding.editTextSearch.text.toString()
            if (topic.isNotEmpty()) {
                val bundle = Bundle()
                bundle.putString("topic", topic)
                val imm: InputMethodManager =
                    requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(requireView().windowToken, 0)
                findNavController().navigate(R.id.action_searchFragment_to_listFragment, bundle)
            } else {
                Toast.makeText(requireContext(), "Field is empty", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }


}