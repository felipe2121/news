package com.example.fortnightly.domain.repository

import br.daniel.fortnightly.domain._config.result.TFResult
import com.example.fortnightly.data.dto.ArticleResponseDTO
import com.example.fortnightly.domain._config.repository.TFRepository

class NewsRemoteRepository: TFRepository.Remote() {

    private val api by retrofit<NewsApi>()

    suspend fun fetchEverything(query: String): TFResult<ArticleResponseDTO> {
        return executeRequest(api) { fetchEverything(query) }
    }
}