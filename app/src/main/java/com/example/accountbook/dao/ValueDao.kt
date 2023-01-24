package com.example.accountbook.dao

import androidx.room.*
import com.example.accountbook.data.Value

@Dao
interface ValueDao {
    @Query("SELECT * FROM value")
    fun getAll(): List<Value>

    @Insert
    fun insert(value: Value)

    @Update
    fun update(value: Value)

    @Delete
    fun delete(value: Value)
}