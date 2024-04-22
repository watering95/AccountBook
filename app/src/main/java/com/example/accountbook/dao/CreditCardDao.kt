package com.example.accountbook.dao

import androidx.room.*
import com.example.accountbook.data.CreditCard
import kotlinx.coroutines.flow.Flow

@Dao
interface CreditCardDao : BaseDao<CreditCard> {
    @Query("SELECT * FROM tbl_CreditCard")
    fun getAll(): Flow<List<CreditCard>>
}