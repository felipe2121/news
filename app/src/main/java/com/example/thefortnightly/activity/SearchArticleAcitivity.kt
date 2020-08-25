package com.example.thefortnightly.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.observe
import com.example.fortnightly.core.state.ViewState
import com.example.fortnightly.core.util.hideKeyboard
import com.example.fortnightly.core.util.observeEvent
import com.example.thefortnightly.R
import com.example.thefortnightly.adapter.ArticlesAdapter
import com.example.thefortnightly.adapter.ArticlesAdapter.AdapterType.SEARCH
import com.example.thefortnightly.view.listener.ArticleClickHandler
import com.example.thefortnightly.viewmodel.SearchArticleViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search.article_list
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchArticleAcitivity: AppCompatActivity(), ArticleClickHandler {

    private val viewModel by viewModel<SearchArticleViewModel>()
    private val articlesAdapter by lazy { ArticlesAdapter(SEARCH) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setUpUI()
        subscribeUI()
    }

    private fun setUpUI() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        article_list.adapter = articlesAdapter
        articlesAdapter.articleClickListener = viewModel

        fun onChanged() {
            if (viewModel.viewState.value is ViewState.LoadingState && articlesAdapter.itemCount != 0) {
                progress.visibility = View.GONE
            }
        }

        search.editText?.setOnEditorActionListener { _, actionId, _ ->

            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.search(search.editText?.text?.toString() ?: "")
                search.editText.hideKeyboard()
                true
            } else {
                false
            }
        }
    }

    private fun subscribeUI() = viewModel.run {
        articles.observe(this@SearchArticleAcitivity) {
            articlesAdapter.refresh(it)
        }
        onArticleClicked.observeEvent(this@SearchArticleAcitivity) {
            this@SearchArticleAcitivity.handleArticleCliked(it)
        }

        viewState.observe(this@SearchArticleAcitivity) { it ->

            progress.visibility = View.GONE
            empty_text.visibility = View.GONE
            error_text.visibility = View.GONE

            when(it) {
                ViewState.LoadingState -> {
                    progress.visibility = if (articlesAdapter.itemCount == 0) View.VISIBLE else View.GONE
                }
                ViewState.EmptyState -> {
                    empty_text.visibility = if (articlesAdapter.itemCount == 0) View.VISIBLE else View.GONE
                }
                is ViewState.ErrorState -> {
                    error_text.visibility = if (articlesAdapter.itemCount == 0) View.VISIBLE else View.GONE
                    if (error_text.visibility == View.GONE) {
                        Snackbar.make(Container, it.message(this@SearchArticleAcitivity), Snackbar.LENGTH_LONG).show()
                    } else {
                        error_text.text = it.message(this@SearchArticleAcitivity)
                    }
                }
            }
        }
    }

}
















