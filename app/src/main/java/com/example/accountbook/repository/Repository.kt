package com.example.accountbook.repository

import kotlinx.coroutines.flow.Flow

interface Repository<T> {
    suspend fun insert(item: T)
    suspend fun update(item: T)
    suspend fun delete(item: T)
    suspend fun delete(array: Array<T>)

    fun getAll(): Flow<List<T>>
}