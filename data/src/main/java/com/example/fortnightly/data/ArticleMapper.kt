package com.example.fortnightly.data

import com.example.fortnightly.data.dto.ArticleDTO
import com.example.fortnightly.data.entiny.ArticleEntity
import com.example.fortnightly.data.ui.Article

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

fun ArticleDTO.toUI() = Article(

    author = this.author ?: "",
    title = this.title ?: "",
    description = this.description ?: "",
    url = this.url,
    urlToImage = this.urlToImage,
    publisedAt = this.publisedAt ?: "",
    content = this.content ?: "",
    category = this.category ?: ""

)

fun List<ArticleDTO>.toUI(): List<Article> {
    return this.map { it.toUI() }
}

fun ArticleEntity.toUI() = Article (

    author = this.author,
    title = this.title,
    description = this.description,
    url = this.url,
    urlToImage = this.urlToImage,
    publisedAt = this.publisedAt,
    content = this.content,
    category = this.category

)


@JvmName("articleEntityToUI")
fun List<ArticleEntity>.toUI(): List<Article> {
    return this.map { it.toUI() }
}
