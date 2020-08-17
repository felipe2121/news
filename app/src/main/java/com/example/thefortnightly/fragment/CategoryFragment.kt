package com.example.thefortnightly.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.thefortnightly.R
import com.example.thefortnightly.adapter.CategoryAdapter
import com.example.thefortnightly.model.Article
import kotlinx.android.synthetic.main.fragment_first_page.*

class CategoryFragment: Fragment() {

    companion object {

        val articleList = mutableListOf<Article>(
            Article("World", "titulo 01", "descricao 01"),
            Article("World", "titulo 02", "descricao 02"),
            Article("World", "titulo 03", "descricao 03"),
            Article("World", "titulo 04", "descricao 04"),
            Article("World", "titulo 05", "descricao 05"),
            Article("World", "titulo 06", "descricao 06")
        )
    }

    private val categoryAdapter by lazy { CategoryAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first_page, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        category_list.adapter = categoryAdapter
        categoryAdapter.refresh(articleList)
    }
}