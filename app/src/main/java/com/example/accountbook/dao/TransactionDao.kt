package com.example.accountbook.dao

import androidx.room.*
import com.example.accountbook.data.Transaction

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transaction")
    fun getAll(): List<Transaction>

    @Insert
    fun insert(transaction: Transaction)

    @Update
    fun update(transaction: Transaction)

    @Delete
    fun delete(transaction: Transaction)
}