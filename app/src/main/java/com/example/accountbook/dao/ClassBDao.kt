package com.example.accountbook.dao

import androidx.room.*
import com.example.accountbook.data.ClassB

@Dao
interface ClassBDao {
    @Query("SELECT * FROM classB")
    fun getAll(): List<ClassB>

    @Insert
    fun insert(classA: ClassB)

    @Update
    fun update(classA: ClassB)

    @Delete
    fun delete(classA: ClassB)
}