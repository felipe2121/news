package com.example.thefortnightly.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.example.fortnightly.data.entiny.ArticleCategory
import com.example.thefortnightly.R
import com.example.thefortnightly.adapter.CategoryAdapter
import com.example.thefortnightly.viewmodel.CategoryNewsViewModel
import kotlinx.android.synthetic.main.fragment_first_page.*
import org.koin.androidx.viewmodel.compat.ScopeCompat.viewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoryFragment: Fragment() {

    private val viewModel by viewModel<CategoryNewsViewModel>()
    private val categoryAdapter by lazy { CategoryAdapter() }

    lateinit var articleCategory: ArticleCategory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first_page, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupUI()
        subscribeUI()
    }

    private fun setupUI() {
        category_list.adapter = categoryAdapter
    }

    private fun subscribeUI() = viewModel.run {

        articles.observe(viewLifecycleOwner) {
                articles -> categoryAdapter.refresh(articles)
        }
    }

}




















