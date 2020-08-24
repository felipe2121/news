package com.example.fortnightly.data._config.dao

import androidx.room.Dao
import androidx.room.Transaction

@Dao
abstract class TransactionDAO {

    @Transaction
    open suspend fun executeTransaction(operations: suspend () -> Unit) {
        operations()
    }
}