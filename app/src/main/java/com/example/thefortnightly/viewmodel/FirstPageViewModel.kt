package com.example.thefortnightly.viewmodel

import androidx.lifecycle.*
import com.example.fortnightly.core.util.Event
import com.example.fortnightly.core.util.SingleMediatorLiveData
import com.example.fortnightly.data.ui.Article
import com.example.fortnightly.domain.usecase.GetArticlesOfFirstPageUseCase
import com.example.thefortnightly.view.listener.ArticleClickListener
import kotlinx.coroutines.launch

class FirstPageViewModel (

    private val getArticlesFirstPage: GetArticlesOfFirstPageUseCase
): ViewModel(), ArticleClickListener {

    override val onArticleClicked = MutableLiveData<Event.Data<Article>>()

    private val _articles = SingleMediatorLiveData<List<Article>>().apply {
        viewModelScope.launch { this@apply.emit(getArticlesFirstPage.liveResult().asLiveData()) }
    }

    val articles = _articles as LiveData<List<Article>>

    fun loadArticles() = viewModelScope.launch {

        getArticlesFirstPage()
            .onStarted {

            }
            .onSuccess {

            }
            .onFailure {

            }
            .onFinish {

            }.execute()
    }
}