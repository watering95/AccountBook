package com.example.accountbook.dao

import androidx.room.*
import com.example.accountbook.data.Account
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao : BaseDao<Account> {
    @Query("SELECT * FROM tbl_Account")
    fun getAll(): Flow<List<Account>>

    @Query("SELECT * from tbl_Account WHERE uid = :id")
    suspend fun get(id: Long): Account
}