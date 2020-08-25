package com.example.thefortnightly.viewmodel

import android.view.View
import androidx.lifecycle.*
import com.example.fortnightly.core.state.ViewState
import com.example.fortnightly.core.util.Event
import com.example.fortnightly.core.util.SingleMediatorLiveData
import com.example.fortnightly.data.entiny.ArticleCategory
import com.example.fortnightly.data.ui.Article
import com.example.fortnightly.domain.usecase.GetArticleOfCategoryUseCase
import com.example.thefortnightly.view.listener.ArticleClickListener
import kotlinx.coroutines.launch

class ArticlesByCategoryViewModel (
    private val getArticleOfCategory: GetArticleOfCategoryUseCase
): ViewModel(), ArticleClickListener {

    override val onArticleClicked = MutableLiveData<Event.Data<Article>>()

    private val _articles = SingleMediatorLiveData<List<Article>>()
    val articles = _articles as LiveData<List<Article>>

    private val _viewState = MutableLiveData<ViewState>()
    val viewState = _viewState as LiveData<ViewState>

    var articleCategory: ArticleCategory? = null
    set(value) {
        if (value != null) {
            field = value
            loadArticlesOfCategory(value)
            viewModelScope.launch {
                _articles.emit(getArticleOfCategory.liveResult(GetArticleOfCategoryUseCase.Params(value)).asLiveData())
            }
        }
    }

    private fun loadArticlesOfCategory(category: ArticleCategory) = viewModelScope.launch {

        getArticleOfCategory(GetArticleOfCategoryUseCase.Params(category))
            .onStarted {
                _viewState.value = ViewState.LoadingState
            }.onSuccess {
                _viewState.value = if (it.isEmpty()) ViewState.EmptyState else ViewState.NoState
            }.onFailure {
                _viewState.value = ViewState.ErrorState(it.errorMessage)
            }.execute()
    }

}

