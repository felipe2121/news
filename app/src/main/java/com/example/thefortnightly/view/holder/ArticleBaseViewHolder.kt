package com.example.thefortnightly.view.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.fortnightly.data.ui.Article
import com.example.thefortnightly.view.listener.ArticleClickListener

abstract class ArticleBaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(article: Article, clickListener: ArticleClickListener?)
}