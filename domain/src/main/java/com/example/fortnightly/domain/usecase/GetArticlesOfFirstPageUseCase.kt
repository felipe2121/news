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

    private val newsReposity: ArticleRepository

): TFUseCase.Completable<Unit, Any, Flow<List<Article>>>() {

    override suspend fun liveResult(params: Unit?) = flow {
        emitAll(newsReposity.getFirstPage().map { it.toUI() })
    }

    override suspend fun execute(params: Unit?): TFResult<Any> {


        return try {
            newsReposity.fetchNewsOfCategory(ArticleCategory.BUSINESS).throwOnFailure()
            newsReposity.fetchNewsOfCategory(ArticleCategory.ENTERTAINMENT).throwOnFailure()
            newsReposity.fetchNewsOfCategory(ArticleCategory.GENERAL).throwOnFailure()
            newsReposity.fetchNewsOfCategory(ArticleCategory.HEALTH).throwOnFailure()
            newsReposity.fetchNewsOfCategory(ArticleCategory.SCIENCE).throwOnFailure()
            newsReposity.fetchNewsOfCategory(ArticleCategory.SPORTS).throwOnFailure()
            newsReposity.fetchNewsOfCategory(ArticleCategory.TECHNOLOGY).throwOnFailure()
            TFResult.success("OK")
        } catch (error: TFException) {
            return TFResult.failure(error)
        }
    }
}