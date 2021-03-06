package com.example.fortnightly.domain.repository

import com.example.fortnightly.data.dto.ArticleResponseDTO
import com.example.fortnightly.domain._config.repository.TFRepository.Companion.API_KEY
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ArticleApi {

    @GET("everything")
    suspend fun fetchEverything (
        @Query("q") query: String,
        @Query("language") country: String = "pt",
        @Header(API_KEY) token: String = "f3de9e4035f145f49a9762c673aa5f8a"
    ): ArticleResponseDTO

    @GET("top-headlines")
    suspend fun fetchTopHeadlines(
        @Query("category") category: String,
        @Query("country") country: String = "br",
        @Header(API_KEY) token: String = "f3de9e4035f145f49a9762c673aa5f8a"
    ): ArticleResponseDTO
}