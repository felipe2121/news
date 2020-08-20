package com.example.fortnightly.data.entiny

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fortnightly.data.dto.SourceDTO


enum class ArticleCategory(val categoryName: String, val optionName: String) {
    BUSINESS("business", "Negócios"),
    ENTERTAINMENT("entertainment", "Entreterimento"),
    GENERAL("general", "Geral"),
    HEALTH("health", "Saúde"),
    SCIENCE("science", "Ciência"),
    SPORTS("sports", "Esportes"),
    TECHNOLOGY("technolohy", "Tecnologia");

    companion object {

        const val ARTICLE_CATEGORY = "article_category"

        fun getByCategoryName(categoryName: String): ArticleCategory {
            return when(categoryName) {
                BUSINESS.categoryName -> BUSINESS
                ENTERTAINMENT.categoryName -> ENTERTAINMENT
                GENERAL.categoryName -> GENERAL
                HEALTH.categoryName -> HEALTH
                SCIENCE.categoryName -> SCIENCE
                SPORTS.categoryName -> SPORTS
                else -> TECHNOLOGY
            }
        }

        fun getOptionsName(optionName: String): ArticleCategory {
            return when(option) {
                "Negócios" -> BUSINESS
                "Entreterimento"-> ENTERTAINMENT
                "Saúde" -> HEALTH
                "Ciência" -> SCIENCE
                "Esportes" -> SPORTS
                "Tecnologia" -> TECHNOLOGY
                else -> GENERAL
        }
    }
}

@Entity(tableName = "article")
data class ArticleEntity (
    var author: String = "",
    var title: String = "",
    var description: String = "",
    var url: String? = null,
    var urlToImage: String? = null,
    var publisedAt: String = "",
    var content: String = "",
    var category: String = "",
    @PrimaryKey(autoGenerate = true) var id: Int = 1
)