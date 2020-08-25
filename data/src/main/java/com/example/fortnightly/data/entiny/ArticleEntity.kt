package com.example.fortnightly.data.entiny

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fortnightly.data.dto.SourceDTO


enum class ArticleCategory(val categoryName: String, val optionName: String) {
    BUSINESS("Business", "Negócios"),
    ENTERTAINMENT("Entertainment", "Entreterimento"),
    GENERAL("General", "Geral"),
    HEALTH("Health", "Saúde"),
    SCIENCE("Science", "Ciência"),
    SPORTS("Sports", "Esportes"),
    TECHNOLOGY("Technology", "Tecnologia");

    companion object {

        const val ARTICLE_CATEGORY = "article_category"

        fun getByCategoryName(categoryName: String): ArticleCategory {
            return when (categoryName) {
                BUSINESS.categoryName -> BUSINESS
                ENTERTAINMENT.categoryName -> ENTERTAINMENT
                GENERAL.categoryName -> GENERAL
                HEALTH.categoryName -> HEALTH
                SCIENCE.categoryName -> SCIENCE
                SPORTS.categoryName -> SPORTS
                else -> TECHNOLOGY
            }
        }

        fun getByOptionsName(optionName: String): ArticleCategory {
            return when (optionName) {
                BUSINESS.optionName -> BUSINESS
                ENTERTAINMENT.optionName -> ENTERTAINMENT
                GENERAL.optionName -> GENERAL
                HEALTH.optionName -> HEALTH
                SCIENCE.optionName -> SCIENCE
                SPORTS.optionName -> SPORTS
                else -> TECHNOLOGY
            }
        }
    }
}
@Entity(tableName = "article")
data class ArticleEntity(
    var author: String = "",
    var title: String = "",
    var description: String = "",
    var url: String? = null,
    var urlToImage: String? = null,
    var publisedAt: String = "",
    var content: String = "",
    var category: String = "",
    @PrimaryKey(autoGenerate = true) var id: Int = 0
)









