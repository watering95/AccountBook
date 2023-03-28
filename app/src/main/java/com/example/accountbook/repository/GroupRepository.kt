package com.example.accountbook.repository

import android.content.Context
import com.example.accountbook.AppRoomDatabase
import com.example.accountbook.data.Group
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface GroupRepository {
    val allGroups: Flow<List<Group>>
    suspend fun insert(group: Group)
}
class GroupRepositoryImpl(private val db : AppRoomDatabase): GroupRepository {
    override val allGroups: Flow<List<Group>> = db.groupDao().getAll()
    override suspend fun insert(group: Group) = db.groupDao().insert(group)
}