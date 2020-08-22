package com.example.thefortnightly.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import com.example.fortnightly.data.ui.Article
import com.example.fortnightly.data.ui.Article.Companion.ARTICLE
import com.example.thefortnightly.R
import com.example.thefortnightly.viewmodel.ArticleViewModel
import kotlinx.android.synthetic.main.activity_article.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArticleActivity: AppCompatActivity() {

    private val viewModel by viewModel<ArticleViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        setupUI()
        subscribeUI()

        if (savedInstanceState == null) {
            viewModel.setArticle(intent?.extras?.get(ARTICLE) as Article)
        }
    }

    private fun setupUI() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    private fun bindUI(article: Article) {
        Glide.with(this)
            .load(article.urlToImage)
            .into(article_image)
            article_title.text = article.title
            article_body.text = article.content
    }

    private fun subscribeUI() = viewModel.run {
        article.observe(this@ArticleActivity) {
            bindUI(it)
        }
    }
}