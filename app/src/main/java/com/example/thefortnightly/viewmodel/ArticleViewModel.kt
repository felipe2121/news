package com.example.thefortnightly.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fortnightly.data.ui.Article

class ArticleViewModel: ViewModel() {

    private val _article = MutableLiveData<Article>()
    val article = _article as LiveData<Article>

    fun setArticle(article: Article) {
        _article.value = article
    }
}