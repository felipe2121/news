package com.example.thefortnightly.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.example.fortnightly.core.util.observeEvent
import com.example.thefortnightly.R
import com.example.thefortnightly.adapter.ArticlesAdapter
import com.example.thefortnightly.adapter.ArticlesAdapter.AdapterType.FIRST_PAGE
import com.example.thefortnightly.view.listener.ArticleClickHandler
import com.example.thefortnightly.viewmodel.FirstPageViewModel
import kotlinx.android.synthetic.main.fragment_list_articles.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FirstPageFragment : Fragment(), ArticleClickHandler {

    private val viewModel by viewModel<FirstPageViewModel>()
    private val articlesAdapter by lazy { ArticlesAdapter(FIRST_PAGE) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_articles, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupUI()
        subscribeUI()
        if (savedInstanceState == null)  viewModel.loadArticles()
    }

    private fun setupUI() {
        article_list.adapter = articlesAdapter
        articlesAdapter.articleClickListener = viewModel
    }

    private fun subscribeUI() = viewModel.run {
        articles.observe(viewLifecycleOwner) {
            articlesAdapter.refresh(it)
        }

        onArticleClicked.observeEvent(viewLifecycleOwner) {article ->
            requireContext().handleArticleCliked(article)
        }
    }
}














