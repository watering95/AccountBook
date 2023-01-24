package com.example.accountbook.dao

import androidx.room.*
import com.example.accountbook.data.Group

@Dao
interface GroupDao {
    @Query("SELECT * FROM group")
    fun getAll(): List<Group>

    @Insert
    fun insert(group: Group)

    @Update
    fun update(group: Group)

    @Delete
    fun delete(goup: Group)
}