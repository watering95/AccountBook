package com.example.accountbook.dao

import androidx.room.*
import com.example.accountbook.data.Transaction

@Dao
interface TransactionDao {
    @Query("SELECT * FROM tbl_Transaction")
    fun getAll(): List<Transaction>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(transaction: Transaction)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(transaction: Transaction)

    @Delete
    suspend fun delete(transaction: Transaction)
}