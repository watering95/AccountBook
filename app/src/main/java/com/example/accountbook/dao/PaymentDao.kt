package com.example.accountbook.dao

import androidx.room.*
import com.example.accountbook.data.Payment

@Dao
interface PaymentDao {
    @Query("SELECT * FROM payment")
    fun getAll(): List<Payment>

    @Insert
    fun insert(payment: Payment)

    @Update
    fun update(payment: Payment)

    @Delete
    fun delete(payment: Payment)
}