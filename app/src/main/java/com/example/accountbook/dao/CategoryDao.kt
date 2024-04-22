package com.example.accountbook.dao

import androidx.room.*
import com.example.accountbook.data.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao : BaseDao<Category> {
    @Query("SELECT * FROM tbl_Category")
    fun getAll(): Flow<List<Category>>

    @Query("SELECT * from tbl_Category WHERE uid = :id")
    suspend fun get(id: Long): Category

    @Query("SELECT * from tbl_Category WHERE idParent = :pid")
    fun getChildrenCategory(pid: Long): Flow<List<Category>>

    @Query("SELECT * from tbl_Category WHERE depth = :depth")
    fun getCategories(depth: Int): Flow<List<Category>>
}