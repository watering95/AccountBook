package com.example.accountbook.repository

import com.example.accountbook.AppRoomDatabase
import com.example.accountbook.data.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    val allCategories: Flow<List<Category>>
    suspend fun insert(category: Category)
    suspend fun update(category: Category)
    suspend fun delete(category: Category)
    suspend fun delete(array: Array<Category>)
    suspend fun getCategory(id: Long): Category
    suspend fun getChildrenCategories(pid: Long): Flow<List<Category>>

    suspend fun getCategories(depth: Int): Flow<List<Category>>
}
class CategoryRepositoryImpl(private val db : AppRoomDatabase): CategoryRepository {
    override val allCategories: Flow<List<Category>> = db.categoryDao().getAll()
    override suspend fun insert(category: Category) = db.categoryDao().insert(category)
    override suspend fun update(category: Category) = db.categoryDao().update(category)
    override suspend fun delete(category: Category) = db.categoryDao().delete(category)
    override suspend fun delete(array: Array<Category>) = db.categoryDao().delete(*array)
    override suspend fun getCategory(id: Long) = db.categoryDao().get(id)
    override suspend fun getChildrenCategories(pid: Long): Flow<List<Category>> = db.categoryDao().getChildrenCategory(pid)
    override suspend fun getCategories(depth: Int): Flow<List<Category>> = db.categoryDao().getCategories(depth)
}