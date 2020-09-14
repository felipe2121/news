package com.example.fortnightly.domain.usecase

import com.example.fortnightly.core.exception.TFException
import com.example.fortnightly.core.util.TFResult
import com.example.fortnightly.core.util.map
import com.example.fortnightly.data.entiny.ArticleCategory
import com.example.fortnightly.data.toEntity
import com.example.fortnightly.data.toUI
import com.example.fortnightly.data.ui.Article
import com.example.fortnightly.domain._config.usecase.TFUseCase
import com.example.fortnightly.domain.repository.ArticleRepository
import com.example.fortnightly.domain.usecase.GetArticleOfCategoryUseCase.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GetArticleOfCategoryUseCase (
    private val newsRepository: ArticleRepository
):TFUseCase.Completable<Params, List<Article>, Flow<List<Article>>>() {

    data class Params(val category: ArticleCategory)

    override suspend fun liveResult(params: Params?) = flow {
        if (params == null) {
            emit(emptyList())
        } else {
            emitAll(newsRepository.getArticlesOfCategory(params.category).map { it.toUI() })
        }
    }

    override suspend fun execute(params: Params?): TFResult<List<Article>> {

        if (params == null) return TFResult.failure(TFException())

            return newsRepository.fetchNewsOfCategory(params.category)
                .map { it.toEntity().toUI() }
    }
}








