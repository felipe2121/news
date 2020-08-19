package com.example.fortnightly.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.fortnightly.data._config.DAO
import com.example.fortnightly.data.entiny.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ArticleDAO: DAO<ArticleEntity>() {

    @Query("SELECT * FROM article")
    abstract suspend fun getAll(): Flow<List<ArticleEntity>>

    @Query("SELECT * FROM article WHERE category = :category")
    abstract fun getByCategory(category: String): Flow<List<ArticleEntity>>
}