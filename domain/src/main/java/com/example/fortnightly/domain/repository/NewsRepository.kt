package com.example.fortnightly.domain.repository

import com.example.fortnightly.core.util.TFResult
import com.example.fortnightly.core.util.onSuccess
import com.example.fortnightly.data.dto.ArticleDTO
import com.example.fortnightly.data.entiny.ArticleCategory
import com.example.fortnightly.data.entiny.ArticleEntity
import com.example.fortnightly.domain._config.repository.TFRepository
import kotlinx.coroutines.flow.map
import java.lang.Exception

class NewsRepository (
    private val newsLocalRepository: NewsLocalRepository,
    private val newsRemoteRepository: NewsRemoteRepository
): TFRepository() {

    suspend fun getFirstPage(): kotlinx.coroutines.flow.Flow<List<ArticleEntity>> {

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
        return newsLocalRepository.getAllAticles().map { articles ->
            mapArticlesByCategory(articles)
        }
    }

    suspend fun getArticlesOfCategory(category: ArticleCategory): kotlinx.coroutines.flow.Flow<List<ArticleEntity>> {
        return newsLocalRepository.getArticlesOfCategory(category)
    }


    suspend fun updateNewsOfCategory (category: ArticleCategory): TFResult<List<ArticleDTO>> {
        return newsRemoteRepository.fetchTopHeadlines(category).onSuccess {articles ->
            newsLocalRepository.saveArticles(articles)

        }
    }

    suspend fun searchNews(query: String): TFResult<List<ArticleDTO>> {

        return newsRemoteRepository.fetchEverything(query)

    }
}



























