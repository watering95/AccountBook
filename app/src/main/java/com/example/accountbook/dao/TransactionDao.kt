package com.example.accountbook.dao

import androidx.room.*
import com.example.accountbook.data.Transaction

@Dao
interface TransactionDao : BaseDao<Transaction> {
    @Query("SELECT * FROM tbl_Transaction")
    fun getAll(): List<Transaction>
}