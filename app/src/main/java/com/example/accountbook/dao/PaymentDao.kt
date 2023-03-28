package com.example.accountbook.dao

import androidx.room.*
import com.example.accountbook.data.Payment

@Dao
interface PaymentDao {
    @Query("SELECT * FROM tbl_Payment")
    fun getAll(): List<Payment>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(payment: Payment)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(payment: Payment)

    @Delete
    suspend fun delete(payment: Payment)
}