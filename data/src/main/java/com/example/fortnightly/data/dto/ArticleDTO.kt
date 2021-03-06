package com.example.fortnightly.data.dto

data class ArticleDTO(
    var sourceDTO: SourceDTO? = null,
    var author: String? = null,
    var title: String? = null,
    var description: String? = null,
    var url: String? = null,
    var category: String? = null,
    var urlToImage: String? = null,
    var publisedAt: String? = null,
    var content: String? = null
)