package com.example.accountbook.dao

import androidx.room.*
import com.example.accountbook.data.Value

@Dao
interface ValueDao {
    @Query("SELECT * FROM tbl_Value")
    fun getAll(): List<Value>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(value: Value)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(value: Value)

    @Delete
    suspend fun delete(value: Value)
}