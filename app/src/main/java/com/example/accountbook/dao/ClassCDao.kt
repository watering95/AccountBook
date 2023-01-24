package com.example.accountbook.dao

import androidx.room.*
import com.example.accountbook.data.ClassC

@Dao
interface ClassCDao {
    @Query("SELECT * FROM classC")
    fun getAll(): List<ClassC>

    @Insert
    fun insert(classC: ClassC)

    @Update
    fun update(classC: ClassC)

    @Delete
    fun delete(classC: ClassC)
}