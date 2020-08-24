package com.example.thefortnightly.view.holder

import android.view.View
import com.bumptech.glide.Glide
import com.example.fortnightly.data.ui.Article
import com.example.thefortnightly.R
import com.example.thefortnightly.view.listener.ArticleClickListener
import kotlinx.android.synthetic.main.view_holder_category_larger.view.*

class ArticleLargeViewHolder(view: View): ArticleBaseViewHolder(view) {

    override fun bind(article: Article, clickListener: ArticleClickListener?) {

        with(itemView) {
            setOnClickListener { clickListener?.onClickArticle(article) }
            category_name.text = article.getFormattedCategory()
            news_title.text = article.title

            Glide.with(this)
                .load(article.urlToImage)
                .placeholder(R.drawable.ic_article_placeholder)
                .into(news_tumbnail)
        }

    }
}