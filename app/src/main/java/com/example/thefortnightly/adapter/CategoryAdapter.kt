package com.example.thefortnightly.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fortnightly.data.ui.Article
import com.example.thefortnightly.R
import com.example.thefortnightly.view.holder.CategoryViewHolder
import com.example.thefortnightly.view.listener.ArticleClickListener

class CategoryAdapter: RecyclerView.Adapter<CategoryViewHolder>() {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_category_small, parent, false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(items[position], articleClickListener)
    }
}