package com.example.thefortnightly.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.example.fortnightly.core.state.ViewState
import com.example.fortnightly.core.util.observeEvent
import com.example.fortnightly.data.entiny.ArticleCategory
import com.example.thefortnightly.R
import com.example.thefortnightly.adapter.ArticlesAdapter
import com.example.thefortnightly.adapter.ArticlesAdapter.AdapterType.CATEGORY
import com.example.thefortnightly.view.listener.ArticleClickHandler
import com.example.thefortnightly.viewmodel.ArticlesByCategoryViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_list_articles.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArticlesByCategoryFragment: Fragment(), ArticleClickHandler {

    private val viewModel by viewModel<ArticlesByCategoryViewModel>()
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
        categoryAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                if (viewModel.viewState.value is ViewState.LoadingState
                    && categoryAdapter.itemCount != 0
                ) {
                    progress.visibility = View.GONE
                }
            }
        })
    }

    private fun subscribeUI() = viewModel.run {

        articles.observe(viewLifecycleOwner) { articles ->
            categoryAdapter.refresh(articles)
        }

        onArticleClicked.observeEvent(viewLifecycleOwner) {article ->
            requireContext().handleArticleCliked(article)
        }

        viewState.observe(viewLifecycleOwner) { it ->

            progress.visibility = View.GONE
            empty_text.visibility = View.GONE
            error_text.visibility = View.GONE

            when(it) {
                ViewState.LoadingState -> {
                    progress.visibility = if (categoryAdapter.itemCount == 0) View.VISIBLE else View.GONE
                }
                ViewState.EmptyState -> {
                    empty_text.visibility = if (categoryAdapter.itemCount == 0) View.VISIBLE else View.GONE
                }
                is ViewState.ErrorState -> {
                    error_text.visibility = if (categoryAdapter.itemCount == 0) View.VISIBLE else View.GONE
                    if (error_text.visibility == View.GONE) {
                        Snackbar.make(container, it.message(requireContext()), Snackbar.LENGTH_LONG).show()
                    } else {
                        error_text.text = it.message(requireContext())
                    }
                }
            }
        }
    }
}




















