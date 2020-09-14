package com.example.fortnightly.domain.repository

import com.example.fortnightly.core.util.TFResult
import com.example.fortnightly.core.util.onSuccess
import com.example.fortnightly.data.dto.ArticleDTO
import com.example.fortnightly.data.entiny.ArticleCategory
import com.example.fortnightly.data.entiny.ArticleEntity
import com.example.fortnightly.domain._config.repository.TFRepository
import kotlinx.coroutines.flow.map
import java.lang.Exception

class ArticleRepository (
    private val articleLocalRepository: ArticleLocalRepository,
    private val articleRemoteRepository: ArticleRemoteRepository
): TFRepository() {

    fun getFirstPage(): kotlinx.coroutines.flow.Flow<List<ArticleEntity>> {

        fun mapArticlesByCategory(articles: List<ArticleEntity>): List<ArticleEntity> {

            val articlesByCategory = mutableListOf<ArticleEntity>()
            articles.groupBy { it.category }.toMutableMap().forEach {
                try {
                    articlesByCategory.add(it.value[0])
                } catch (error: Exception) {
                }
            }
            return articlesByCategory
        }
        return articleLocalRepository.getAllArticles().map { articles ->
            mapArticlesByCategory(articles)
        }
    }

    fun getArticlesOfCategory(category: ArticleCategory): kotlinx.coroutines.flow.Flow<List<ArticleEntity>> {
        return articleLocalRepository.getArticlesOfCategory(category)
    }

    suspend fun fetchNewsOfCategory (category: ArticleCategory): TFResult<List<ArticleDTO>> {
        return articleRemoteRepository.fetchTopHeadlines(category)
            .onSuccess { articles ->
            articleLocalRepository.saveArticlesOfCategory(articles, category)
        }
    }

    suspend fun fetchArticlesOfQuery(query: String): TFResult<List<ArticleDTO>> {
        return articleRemoteRepository.fetchEverything(query)
    }
}



























