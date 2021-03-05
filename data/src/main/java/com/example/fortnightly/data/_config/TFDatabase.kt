package com.example.fortnightly.data._config

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fortnightly.data.BuildConfig
import com.example.fortnightly.data._config.dao.TransactionDAO
import com.example.fortnightly.data.dao.ArticleDAO
import com.example.fortnightly.data.entiny.ArticleEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Database(
    version = 2,
    exportSchema = false,
    entities = [
        ArticleEntity::class
    ]
)
abstract class TFDatabase : RoomDatabase() {

    companion object {

        private var instance: TFDatabase? = null
        private var dropJob: Job? = null

        fun getInstance(context: Context): TFDatabase {
            return instance ?: synchronized(this) {
                instance ?: synchronized(this) {
                    instance = buildDatabase(context)
                    instance!!
                }
            }
        }

        suspend fun dropDatabase () {
            dropJob = GlobalScope.launch(Dispatchers.Default) {
                instance?.runInTransaction { instance?.clearAllTables() }
            }.apply { join() }
        }

        private fun buildDatabase(context: Context): TFDatabase {
            return Room.databaseBuilder(context, TFDatabase::class.java, "TF_DB")
                .fallbackToDestructiveMigration()
                .build()
        }

    }

    abstract val transactionDAO: TransactionDAO
    abstract val articleDAO: ArticleDAO

}