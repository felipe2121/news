package com.example.fortnightly.data.entiny

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fortnightly.data.dto.SourceDTO


enum class ArticleCategory(val categoryName: String) {
    BUSINESS("business"),
    ENTERTAINMENT("entertainment"),
    GENERAL("general"),
    HEALTH("health"),
    SCIENCE("science"),
    SPORTS("sports"),
    TECHNOLOGY("technolohy");

    companion object {
        fun get(categoryName: String): ArticleCategory {
            return when(categoryName) {
                BUSINESS.categoryName -> BUSINESS
                ENTERTAINMENT.categoryName -> ENTERTAINMENT
                GENERAL.categoryName -> GENERAL
                HEALTH.categoryName -> HEALTH
                SCIENCE.categoryName -> SCIENCE
                SPORTS.categoryName -> SPORTS
                TECHNOLOGY.categoryName -> TECHNOLOGY
                else -> TECHNOLOGY
            }
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