package com.example.accountbook.screen.groupsetscreen

import com.example.accountbook.AppRoomDatabase
import com.example.accountbook.data.Group
import com.example.accountbook.repository.Repository
import kotlinx.coroutines.flow.Flow

interface GroupSetScreenRepository : Repository<Group> {
    val allGroups: Flow<List<Group>>
}
class GroupSetScreenRepositoryImpl(private val db : AppRoomDatabase): GroupSetScreenRepository {
    override val allGroups: Flow<List<Group>> = db.groupDao().getAll()
    override suspend fun insert(item: Group) = db.groupDao().insert(item)
    override suspend fun update(item: Group) = db.groupDao().update(item)
    override suspend fun delete(item: Group) = db.groupDao().delete(item)
    override suspend fun delete(array: Array<Group>) = db.groupDao().delete(*array)
    override fun getAll(): Flow<List<Group>> = db.groupDao().getAll()
}