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
import com.example.qnews.ui.viewModel.other.MainViewModel
import com.example.qnews.ui.recycler.adapters.NewsAdapter
import com.example.qnews.ui.recycler.listeners.OnRecyclerClickListener

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

        val bundle: Bundle? = this.arguments

        bundle?.let {
            val topic = it.getString(getString(R.string.topic))
            if (topic != null) {
                if (viewModel.listOFNews.value == null)
                    viewModel.getNewsByTopic(topic)
            } else {
                Toast.makeText(requireContext(), getString(R.string.error), Toast.LENGTH_SHORT).show()
            }
        }

        val newsAdapter = NewsAdapter()
        binding.recyclerViewNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        NewsAdapter.setOnNewsClickListener(object : OnRecyclerClickListener {
            override fun onClick(position: Int) {
                val id = viewModel.listOFNews.value!![position].uniqueId
                val bundle1 = Bundle()
                bundle1.putInt("id", id)
                findNavController().navigate(R.id.action_listFragment_to_detailFragment, bundle1)
            }
        })

        viewModel.listOFNews.observe(viewLifecycleOwner) {
            if (it != null) {
                newsAdapter.addListOfNews(it)
            }
        }

        if (viewModel.listOFNews.value == null) {
            if (bundle == null)
                viewModel.getAllNews()
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.loop, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        findNavController().navigate(R.id.action_listFragment_to_searchFragment)
        return super.onOptionsItemSelected(item)
    }


}