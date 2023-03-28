package com.example.accountbook.dao

import androidx.room.*
import com.example.accountbook.data.Category

@Dao
interface CategoryDao {
    @Query("SELECT * FROM tbl_Category")
    fun getAll(): List<Category>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(category: Category)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(category: Category)

    @Delete
    suspend fun delete(category: Category)
}