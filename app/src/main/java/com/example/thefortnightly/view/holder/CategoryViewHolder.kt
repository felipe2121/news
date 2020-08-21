package com.example.thefortnightly.view.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fortnightly.data.ui.Article
import com.example.thefortnightly.view.listener.ArticleClickListener
import kotlinx.android.synthetic.main.view_holder_category_small.view.*

class CategoryViewHolder(view: View): RecyclerView.ViewHolder(view) {

    fun bind(article: Article, clickListener: ArticleClickListener?) {
        with(itemView) {
            setOnClickListener { clickListener?.onClickListener(article) }
            category_name.text = article.category
            news_title.text = article.title

            Glide.with(this)
                .load("https://marginalrevolution.com/wp-content/uploads/2016/10/MR-logo-thumbnail.png")
                .into(news_tumbnail)
        }
    }
}