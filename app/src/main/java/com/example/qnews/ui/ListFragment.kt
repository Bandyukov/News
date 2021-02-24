package com.example.qnews.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qnews.R
import com.example.qnews.databinding.FragmentListBinding
import com.example.qnews.ui.recycler.NewsAdapter
import com.example.qnews.ui.recycler.OnNewsClickListener

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
            val topic = it.getString("topic")
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

        NewsAdapter.setOnNewsClickListener(object : OnNewsClickListener {
            override fun onNewsClick(position: Int) {
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
            Log.i("qwe", "View Model null")
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