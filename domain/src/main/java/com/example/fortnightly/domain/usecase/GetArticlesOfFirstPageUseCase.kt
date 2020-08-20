package com.example.fortnightly.domain.usecase

import com.example.fortnightly.core.exception.TFException
import com.example.fortnightly.core.util.TFResult
import com.example.fortnightly.core.util.throwOnFailure
import com.example.fortnightly.data.entiny.ArticleCategory
import com.example.fortnightly.data.toUI
import com.example.fortnightly.data.ui.Article
import com.example.fortnightly.domain._config.usecase.TFUseCase
import com.example.fortnightly.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.lang.Exception

class GetArticlesOfFirstPageUseCase (

    private val newsReposity: NewsRepository

): TFUseCase.Completable<Unit, Any, Flow<List<Article>>>() {

    override suspend fun liveResult(params: Unit?) = flow {
        emitAll(newsReposity.getFirstPage().map { it.toUI() })
    }

    override suspend fun execute(params: Unit?): TFResult<Any> {


        return try {
            newsReposity.updateNewsOfCategory(ArticleCategory.BUSINESS).throwOnFailure()
            newsReposity.updateNewsOfCategory(ArticleCategory.ENTERTAINMENT).throwOnFailure()
            newsReposity.updateNewsOfCategory(ArticleCategory.GENERAL).throwOnFailure()
            newsReposity.updateNewsOfCategory(ArticleCategory.HEALTH).throwOnFailure()
            newsReposity.updateNewsOfCategory(ArticleCategory.SCIENCE).throwOnFailure()
            newsReposity.updateNewsOfCategory(ArticleCategory.SPORTS).throwOnFailure()
            newsReposity.updateNewsOfCategory(ArticleCategory.TECHNOLOGY).throwOnFailure()
            TFResult.success("OK")
        } catch (error: TFException) {
            return TFResult.failure(error)
        }
    }
}