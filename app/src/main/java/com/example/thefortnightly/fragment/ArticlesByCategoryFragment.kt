package com.example.thefortnightly.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.example.fortnightly.core.util.observeEvent
import com.example.fortnightly.data.entiny.ArticleCategory
import com.example.fortnightly.data.ui.Article.Companion.ARTICLE
import com.example.thefortnightly.R
import com.example.thefortnightly.activity.ArticleActivity
import com.example.thefortnightly.adapter.ArticlesAdapter
import com.example.thefortnightly.adapter.ArticlesAdapter.AdapterType.CATEGORY
import com.example.thefortnightly.adapter.ArticlesAdapter.AdapterType.FIRST_PAGE
import com.example.thefortnightly.view.listener.ArticleClickHandler
import com.example.thefortnightly.viewmodel.CategoryNewsViewModel
import kotlinx.android.synthetic.main.fragment_list_articles.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArticlesByCategoryFragment: Fragment(), ArticleClickHandler {

    private val viewModel by viewModel<CategoryNewsViewModel>()
    private val categoryAdapter by lazy { ArticlesAdapter(CATEGORY) }

    lateinit var articleCategory: ArticleCategory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        articleCategory = this.arguments?.get(ArticleCategory.ARTICLE_CATEGORY) as ArticleCategory
        return inflater.inflate(R.layout.fragment_list_articles, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupUI()
        subscribeUI()

        if (savedInstanceState == null) {
            viewModel.articleCategory = articleCategory
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    private fun setupUI() {
        article_list.adapter = categoryAdapter
        categoryAdapter.articleClickListener = viewModel
    }

    private fun subscribeUI() = viewModel.run {

        articles.observe(viewLifecycleOwner) { articles ->
            categoryAdapter.refresh(articles)
        }

        onArticleClicked.observeEvent(viewLifecycleOwner) {article ->
            requireContext().handleArticleCliked(article)
        }
    }

}




















