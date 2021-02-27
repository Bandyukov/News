package com.example.qnews.ui.recycler.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.qnews.R
import com.example.qnews.ui.recycler.listeners.OnRecyclerClickListener

class SuggestionAdapter : RecyclerView.Adapter<SuggestionAdapter.SuggestionViewHolder>() {

    private var suggestions = listOf<String>()

    private lateinit var onRecyclerClickListener : OnRecyclerClickListener

    fun setOnNewClickListener(onRecyclerClickListener: OnRecyclerClickListener) {
        this.onRecyclerClickListener = onRecyclerClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.suggestion_item, parent, false)
        return SuggestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: SuggestionViewHolder, position: Int) {
        val suggestion = suggestions[position]
        holder.bind(suggestion)
    }

    override fun getItemCount(): Int = suggestions.size

    fun setSuggestions(suggestions: List<String>) {
        this.suggestions = suggestions
        notifyDataSetChanged()
    }

    inner class SuggestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val suggestionTextView = itemView.findViewById<TextView>(R.id.suggestionTextView)

        init {
            itemView.setOnClickListener {
                onRecyclerClickListener.onClick(adapterPosition)
            }
        }

        fun bind(suggestion: String) {
            suggestionTextView.text = suggestion
        }

//        companion object {
//            fun from(parent: ViewGroup): SuggestionViewHolder {
//                val view = LayoutInflater.from(parent.context)
//                    .inflate(R.layout.suggestion_item, parent, false)
//                return SuggestionViewHolder(view)
//            }
//        }

    }
}