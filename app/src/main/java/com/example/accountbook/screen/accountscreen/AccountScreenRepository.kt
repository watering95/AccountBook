package com.example.accountbook.screen.accountscreen

import com.example.accountbook.AppRoomDatabase
import com.example.accountbook.data.AccountLog
import kotlinx.coroutines.flow.Flow

interface AccountScreenRepository {
    val allAccountLogs: Flow<List<AccountLog>>
}

class AccountScreenRepositoryImpl(db: AppRoomDatabase) : AccountScreenRepository {
    override val allAccountLogs: Flow<List<AccountLog>> = db.accountLogDao().getAll()
}