package com.example.fortnightly.domain.repository

import com.example.fortnightly.core.util.TFResult
import com.example.fortnightly.core.util.map
import com.example.fortnightly.core.util.onSuccess
import com.example.fortnightly.data.dto.ArticleDTO
import com.example.fortnightly.data.entiny.ArticleCategory
import com.example.fortnightly.domain._config.repository.TFRepository

class ArticleRemoteRepository: TFRepository.Remote() {

    private val api by retrofit<ArticleApi>()

    suspend fun fetchEverything(query: String): TFResult<List<ArticleDTO>> {
        return executeRequest(api) { fetchEverything(query) }
            .map { it.articles?: emptyList() }
    }

    suspend fun fetchTopHeadlines(category: ArticleCategory): TFResult<List<ArticleDTO>> {
        return executeRequest(api) { fetchTopHeadlines(category.categoryName) }
            .onSuccess { response ->
                response.articles?.forEach { it.category = category.categoryName }
            }.map { it.articles?: emptyList() }
    }
}