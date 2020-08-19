package com.example.fortnightly.domain.repository

import com.example.fortnightly.data.entiny.ArticleEntity
import com.example.fortnightly.domain._config.repository.TFRepository
import kotlinx.coroutines.flow.map
import java.lang.Exception
import java.util.concurrent.Flow

class NewsRepository (

    private val newsLocalRepository: NewsLocalRepository,
    private val newsRemoteRepository: NewsRemoteRepository
): TFRepository() {

    /*suspend fun getFirstPage(): Flow<List<ArticleEntity>> {
        return newsLocalRepository.getAll().map { articles ->
            val articlesByCategory = mutableListOf<ArticleEntity>()
            articles.groupBy { it.category }.toMutableMap().forEach {
                try {
                    articlesByCategory.add(it.value[0])
                } catch (error: Exception) {
                }
            }
            articlesByCategory
        }
    }*/
}