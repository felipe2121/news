package com.example.fortnightly.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.fortnightly.data._config.dao.DAO
import com.example.fortnightly.data.entiny.ArticleEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.http.DELETE

@Dao
abstract class ArticleDAO: DAO<ArticleEntity>() {

    @Query("SELECT * FROM article")
    abstract fun getAll(): Flow<List<ArticleEntity>>

    @Query("SELECT * FROM article WHERE category = :category")
    abstract fun getByCategory(category: String): Flow<List<ArticleEntity>>

    @Query("DELETE FROM article WHERE category = :category")
    abstract suspend fun deleteByCategory(category: String)

}