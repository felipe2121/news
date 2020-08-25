package com.example.thefortnightly.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        if (item.itemId == R.id.action_share) {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)

            return true
        } else {
            return super.onOptionsItemSelected(item)
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
            .placeholder(R.drawable.ic_article_placeholder)
            .into(article_image)
            article_title.text = article.title
            article_description.text = article.description
            article_body.text = article.content
    }

    private fun subscribeUI() = viewModel.run {
        article.observe(this@ArticleActivity) {
            bindUI(it)
        }
    }
}