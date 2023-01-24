package com.example.accountbook.dao

import androidx.room.*
import com.example.accountbook.data.ClassA

@Dao
interface ClassADao {
    @Query("SELECT * FROM classA")
    fun getAll(): List<ClassA>

    @Insert
    fun insert(classA: ClassA)

    @Update
    fun update(classA: ClassA)

    @Delete
    fun delete(classA: ClassA)
}