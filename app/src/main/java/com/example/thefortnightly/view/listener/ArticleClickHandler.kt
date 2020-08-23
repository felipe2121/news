package com.example.thefortnightly.view.listener

import android.content.Context
import android.content.Intent
import com.example.fortnightly.data.ui.Article
import com.example.thefortnightly.activity.ArticleActivity

interface ArticleClickHandler {

    fun Context.handleArticleCliked(article: Article) {
        Intent(this, ArticleActivity::class.java).also { intent ->
            intent.putExtra(Article.ARTICLE, article)
            startActivity(intent)
        }
    }
}