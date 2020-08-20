package com.example.fortnightly.domain.repository

import com.example.fortnightly.data.dao.ArticleDAO
import com.example.fortnightly.data.dto.ArticleDTO
import com.example.fortnightly.data.entiny.ArticleCategory
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

    suspend fun getAllAticles(): Flow<List<ArticleEntity>> {
        return articleDAO.getAll()
    }

    fun getArticlesOfCategory(category: ArticleCategory): Flow<List<ArticleEntity>> {
        return articleDAO.getByCategory(category.categoryName)
    }

}
