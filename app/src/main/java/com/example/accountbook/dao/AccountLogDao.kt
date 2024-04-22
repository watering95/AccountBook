package com.example.accountbook.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.accountbook.data.AccountLog
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountLogDao {
    @Query("SELECT * FROM tbl_AccountLog")
    fun getAll(): Flow<List<AccountLog>>
}