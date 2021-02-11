package com.example.qnews.ui.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.qnews.R
import com.example.qnews.core.models.News

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    var listOfNews = listOf<News>()

    companion object {
        private lateinit var onNewsClickListener: OnNewsClickListener

        fun setOnNewsClickListener(onNewsClickListener: OnNewsClickListener) {
            this.onNewsClickListener = onNewsClickListener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = listOfNews[position]
        holder.bind(news)
    }

    override fun getItemCount(): Int = listOfNews.size

    fun addListOfNews(news: List<News>) {
        listOfNews = news
        notifyDataSetChanged()
    }

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewTitle = itemView.findViewById<TextView>(R.id.textViewTitle)
        private val textViewDate = itemView.findViewById<TextView>(R.id.textViewDate)
        private val imageViewPoster = itemView.findViewById<ImageView>(R.id.imageViewPoster)

        init {
            itemView.setOnClickListener {
                onNewsClickListener.onNewsClick(adapterPosition)
            }
        }

        fun bind(news: News) {
            textViewTitle.text = news.title
            textViewDate.text = news.publishedAt
            val options = RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.connection_error_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
            Glide.with(imageViewPoster.context)
                .applyDefaultRequestOptions(options)
                .load(news.urlToImage)
                .into(imageViewPoster)
        }


    }
}