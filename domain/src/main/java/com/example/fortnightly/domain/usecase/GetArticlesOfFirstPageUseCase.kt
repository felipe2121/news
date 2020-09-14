package com.example.fortnightly.domain.usecase

import com.example.fortnightly.core.exception.TFException
import com.example.fortnightly.core.util.TFResult
import com.example.fortnightly.core.util.throwOnFailure
import com.example.fortnightly.data.entiny.ArticleCategory
import com.example.fortnightly.data.toUI
import com.example.fortnightly.data.ui.Article
import com.example.fortnightly.domain._config.usecase.TFUseCase
import com.example.fortnightly.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GetArticlesOfFirstPageUseCase (
    private val newsRepository: ArticleRepository
): TFUseCase.Completable<Unit, Any, Flow<List<Article>>>() {

    override suspend fun liveResult(params: Unit?) = flow {
        emitAll(newsRepository.getFirstPage().map { it.toUI() })
    }

    override suspend fun execute(params: Unit?): TFResult<Any> {
        return try {
            newsRepository.fetchNewsOfCategory(ArticleCategory.BUSINESS).throwOnFailure()
            newsRepository.fetchNewsOfCategory(ArticleCategory.ENTERTAINMENT).throwOnFailure()
            newsRepository.fetchNewsOfCategory(ArticleCategory.GENERAL).throwOnFailure()
            newsRepository.fetchNewsOfCategory(ArticleCategory.HEALTH).throwOnFailure()
            newsRepository.fetchNewsOfCategory(ArticleCategory.SCIENCE).throwOnFailure()
            newsRepository.fetchNewsOfCategory(ArticleCategory.SPORTS).throwOnFailure()
            newsRepository.fetchNewsOfCategory(ArticleCategory.TECHNOLOGY).throwOnFailure()
            TFResult.success("OK")
        } catch (error: TFException) {
            return TFResult.failure(error)
        }
    }
}