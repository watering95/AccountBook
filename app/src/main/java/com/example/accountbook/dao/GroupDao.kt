package com.example.accountbook.dao

import androidx.room.*
import com.example.accountbook.data.Group
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupDao : BaseDao<Group> {
    @Query("SELECT * from tbl_group")
    fun getAll(): Flow<List<Group>>

    @Query("SELECT * from tbl_group WHERE uid = :id")
    suspend fun get(id: Long): Group

    @Query("DELETE from tbl_group")
    suspend fun deleteAll()
}