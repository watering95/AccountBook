package com.example.accountbook.screen.homescreen

import com.example.accountbook.AppRoomDatabase
import com.example.accountbook.data.Account
import com.example.accountbook.data.Group
import com.example.accountbook.repository.Repository
import kotlinx.coroutines.flow.Flow

interface HomeScreenRepository : Repository<Account> {
    val allAccounts: Flow<List<Account>>
    val allGroups: Flow<List<Group>>
}

class HomeScreenRepositoryImpl(db : AppRoomDatabase): HomeScreenRepository {
    override val allAccounts: Flow<List<Account>> = db.accountDao().getAll()
    override val allGroups: Flow<List<Group>> = db.groupDao().getAll()
    override suspend fun insert(item: Account) {
        TODO("Not yet implemented")
    }

    override suspend fun update(item: Account) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(item: Account) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(array: Array<Account>) {
        TODO("Not yet implemented")
    }

    override fun getAll(): Flow<List<Account>> {
        TODO("Not yet implemented")
    }
}