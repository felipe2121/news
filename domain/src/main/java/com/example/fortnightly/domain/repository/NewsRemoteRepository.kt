package com.example.fortnightly.domain.repository

import br.daniel.fortnightly.domain._config.result.TFResult
import br.daniel.fortnightly.domain._config.result.onSuccess
import com.example.fortnightly.data.dto.ArticleResponseDTO
import com.example.fortnightly.data.entiny.ArticleCategory
import com.example.fortnightly.domain._config.repository.TFRepository

class NewsRemoteRepository: TFRepository.Remote() {

    private val api by retrofit<NewsApi>()

    suspend fun fetchEverything(query: String): TFResult<ArticleResponseDTO> {
        return executeRequest(api) { fetchEverything(query) }
    }

    suspend fun fetchTopHeadlines(category: ArticleCategory): TFResult<ArticleResponseDTO> {
        return executeRequest(api) { fetchTopHeadlines(category.categoryName) }
            .onSuccess {response ->
                response.articles?.forEach { it.category = category.categoryName }
            }
    }
}