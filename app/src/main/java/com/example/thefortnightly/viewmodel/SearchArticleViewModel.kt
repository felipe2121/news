package com.example.thefortnightly.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fortnightly.core.state.ViewState
import com.example.fortnightly.core.util.Event
import com.example.fortnightly.data.ui.Article
import com.example.fortnightly.domain.usecase.SearchArticleUseCase
import com.example.thefortnightly.view.listener.ArticleClickListener
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch

class SearchArticleViewModel (
    private val searchArticle: SearchArticleUseCase
): ViewModel(), ArticleClickListener {

    override val onArticleClicked = MutableLiveData<Event.Data<Article>>()

    private val _articles = MutableLiveData<List<Article>>()
    val articles = _articles as LiveData<List<Article>>

    private val _viewState = MutableLiveData<ViewState>()
    val viewState = _viewState as LiveData<ViewState>

    private var searchJob: Job? = null

    fun search(query: String) = viewModelScope.launch {

        searchJob?.cancelAndJoin()
        searchJob = viewModelScope.launch {

            searchArticle(SearchArticleUseCase.Params(query))
                .onStarted {
                    _viewState.value = ViewState.LoadingState
                }.onSuccess {
                    _viewState.value = if (it.isEmpty()) ViewState.EmptyState else ViewState.NoState
                    _articles.value = it
                }.onFailure {
                    _viewState.value = ViewState.ErrorState(it.errorMessage)
                }.execute()

        }
    }

}