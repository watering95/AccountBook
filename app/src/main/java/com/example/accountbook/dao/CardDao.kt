package com.example.accountbook.dao

import androidx.room.*
import com.example.accountbook.data.Card

@Dao
interface CardDao {
    @Query("SELECT * FROM tbl_Card")
    fun getAll(): List<Card>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(account: Card)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(account: Card)

    @Delete
    suspend fun delete(account: Card)
}