package com.example.fortnightly.domain.repository

import com.example.fortnightly.data.dao.ArticleDAO
import com.example.fortnightly.data.dto.ArticleDTO
import com.example.fortnightly.data.entiny.ArticleEntity
import com.example.fortnightly.data.toEntity
import com.example.fortnightly.domain._config.repository.TFRepository
import kotlinx.coroutines.flow.Flow

class NewsLocalRepository (
    private val articleDAO: ArticleDAO
): TFRepository.Local() {

    suspend fun saveArticles(articles: List<ArticleDTO>) {
        articleDAO.insertOrUpdate(articles.toEntity())
    }

    suspend fun getAll(): Flow<List<ArticleEntity>> {

        return articleDAO.getAll()
    }

}
