package com.example.accountbook.dao

import androidx.room.*
import com.example.accountbook.data.Card
import com.example.accountbook.data.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("SELECT * FROM tbl_Category")
    fun getAll(): Flow<List<Category>>

    @Query("SELECT * from tbl_Category WHERE uid = :id")
    suspend fun get(id: Int): Category

    @Query("SELECT * from tbl_Category WHERE idParent = :pid")
    fun getChildrenCategory(pid: Int): Flow<List<Category>>

    @Query("SELECT * from tbl_Category WHERE depth = :depth")
    fun getCategories(depth: Int): Flow<List<Category>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(category: Category)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(category: Category)

    @Delete
    suspend fun delete(category: Category)

    @Delete
    suspend fun delete(vararg categories: Category)
}