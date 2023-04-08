package com.example.accountbook.dao

import androidx.room.*
import com.example.accountbook.data.Group
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupDao {
    @Query("SELECT * from tbl_group")
    fun getAll(): Flow<List<Group>>

    @Query("DELETE from tbl_group")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(group: Group)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(group: Group)

    @Delete
    suspend fun delete(group: Group)

    @Delete
    suspend fun delete(vararg groups: Group)
}