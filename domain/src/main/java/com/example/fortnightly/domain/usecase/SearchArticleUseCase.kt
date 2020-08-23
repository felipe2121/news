package com.example.fortnightly.domain.usecase

import com.example.fortnightly.core.exception.TFException
import com.example.fortnightly.core.util.TFResult
import com.example.fortnightly.core.util.map
import com.example.fortnightly.data.toUI
import com.example.fortnightly.data.ui.Article
import com.example.fortnightly.domain._config.usecase.TFUseCase
import com.example.fortnightly.domain.repository.NewsRepository
import com.example.fortnightly.domain.usecase.SearchArticleUseCase.Params

class SearchArticleUseCase (

    private val newsRepository: NewsRepository

): TFUseCase.Completable<Params, List<Article>, Unit>() {

    data class Params(val query: String)

    override suspend fun execute(params: Params?): TFResult<List<Article>> {
        if (params == null) return TFResult.failure(TFException())
        return newsRepository.searchNews(params.query).map { it.toUI() }
    }
}