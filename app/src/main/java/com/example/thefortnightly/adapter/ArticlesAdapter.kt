package com.example.thefortnightly.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fortnightly.data.ui.Article
import com.example.thefortnightly.R
import com.example.thefortnightly.adapter.ArticlesAdapter.AdapterType.*
import com.example.thefortnightly.adapter.ArticlesAdapter.ViewType.*
import com.example.thefortnightly.view.holder.ArticleBaseViewHolder
import com.example.thefortnightly.view.holder.ArticleLargeViewHolder
import com.example.thefortnightly.view.holder.ArticleSmallViewHolder
import com.example.thefortnightly.view.listener.ArticleClickListener

class ArticlesAdapter(private val adapterType: AdapterType): RecyclerView.Adapter<ArticleBaseViewHolder>() {

    enum class AdapterType { FIRST_PAGE, CATEGORY, SEARCH }
    enum class ViewType(val id: Int) { ARTICLE_SMALL(0), ARTICLE_LARGE(1) }

    private val items: MutableList<Article> = mutableListOf()
    var articleClickListener: ArticleClickListener? = null

    fun refresh(newItems: List<Article>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return when(adapterType) {
            FIRST_PAGE -> if (position == 0) ARTICLE_LARGE.id else ARTICLE_SMALL.id
            CATEGORY, SEARCH -> ARTICLE_SMALL.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleBaseViewHolder {

        return when(viewType) {
            ARTICLE_LARGE.id -> ArticleLargeViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.view_holder_category_larger,
                        parent,
                        false
                    )
            )
            else -> ArticleSmallViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.view_holder_category_small,
                        parent,
                        false
                    )
            )
        }
    }

    override fun onBindViewHolder(holder: ArticleBaseViewHolder, position: Int) {
        holder.bind(items[position], articleClickListener)
    }
}