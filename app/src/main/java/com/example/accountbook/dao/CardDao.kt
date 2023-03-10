package com.example.accountbook.dao

import androidx.room.*
import com.example.accountbook.data.Card

@Dao
interface CardDao {
    @Query("SELECT * FROM account")
    fun getAll(): List<Card>

    @Insert
    fun insert(account: Card)

    @Update
    fun update(account: Card)

    @Delete
    fun delete(account: Card)
}