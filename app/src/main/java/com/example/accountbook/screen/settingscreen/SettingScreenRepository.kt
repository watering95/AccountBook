package com.example.accountbook.screen.settingscreen

import com.example.accountbook.AppRoomDatabase
import com.example.accountbook.data.Account
import com.example.accountbook.repository.Repository
import kotlinx.coroutines.flow.Flow


interface SettingScreenRepository: Repository<Account>

class SettingScreenRepositoryImpl(db : AppRoomDatabase): SettingScreenRepository {
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