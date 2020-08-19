package com.example.fortnightly.data

import com.example.fortnightly.data.dto.ArticleDTO
import com.example.fortnightly.data.entiny.ArticleEntity

fun ArticleDTO.toEntity() = ArticleEntity(

    author = this.author ?: "",
    title = this.title ?: "",
    description = this.description ?: "",
    url = this.url,
    urlToImage = this.urlToImage,
    publisedAt = this.publisedAt ?: "",
    content = this.content ?: "",
    category = this.category ?: ""
)

fun List<ArticleDTO>.toEntity(): List<ArticleEntity> {
    return this.map { it.toEntity() }
}