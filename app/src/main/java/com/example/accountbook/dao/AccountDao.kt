package com.example.accountbook.dao

import androidx.room.*
import com.example.accountbook.data.Account
import com.example.accountbook.data.Group
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {
    @Query("SELECT * FROM tbl_Account")
    fun getAll(): Flow<List<Account>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(account: Account)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(account: Account)

    @Delete
    suspend fun delete(account: Account)

    @Delete
    suspend fun delete(vararg accounts: Account)
}