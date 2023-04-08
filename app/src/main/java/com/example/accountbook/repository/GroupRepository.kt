package com.example.accountbook.repository

import com.example.accountbook.AppRoomDatabase
import com.example.accountbook.data.Group
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

interface GroupRepository {
    val allGroups: Flow<List<Group>>
    suspend fun insert(group: Group)
    suspend fun update(group: Group)
    suspend fun delete(group: Group)
    suspend fun delete(array: Array<Group>)
}
class GroupRepositoryImpl(private val db : AppRoomDatabase): GroupRepository {
    override val allGroups: Flow<List<Group>> = db.groupDao().getAll()
    override suspend fun insert(group: Group) = db.groupDao().insert(group)
    override suspend fun update(group: Group) = db.groupDao().update(group)
    override suspend fun delete(group: Group) = db.groupDao().delete(group)
    override suspend fun delete(array: Array<Group>) = db.groupDao().delete(*array)
}