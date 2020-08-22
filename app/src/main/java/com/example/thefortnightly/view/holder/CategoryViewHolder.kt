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
            setOnClickListener { clickListener?.onClickArticle(article) }
            category_name.text = article.category
            news_title.text = article.title

            Glide.with(this)
                .load(article.urlToImage)
                .into(news_tumbnail)
        }

    }
}