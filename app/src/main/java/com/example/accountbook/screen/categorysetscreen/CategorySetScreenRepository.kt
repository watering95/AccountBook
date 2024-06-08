package com.example.accountbook.screen.categorysetscreen

import com.example.accountbook.AppRoomDatabase
import com.example.accountbook.data.Category
import com.example.accountbook.repository.Repository
import kotlinx.coroutines.flow.Flow

interface CategoryRepository: Repository<Category> {
    val allCategories: Flow<List<Category>>
    suspend fun getCategory(id: Long): Category
    suspend fun getChildrenCategories(pid: Long): Flow<List<Category>>

    suspend fun getCategories(depth: Int): Flow<List<Category>>
}
class CategorySetScreenRepositoryImpl(private val db : AppRoomDatabase): CategoryRepository {
    override val allCategories: Flow<List<Category>> = db.categoryDao().getAll()
    override suspend fun insert(item: Category) = db.categoryDao().insert(item)
    override suspend fun update(item: Category) = db.categoryDao().update(item)
    override suspend fun delete(item: Category) = db.categoryDao().delete(item)
    override suspend fun delete(array: Array<Category>) = db.categoryDao().delete(*array)
    override fun getAll(): Flow<List<Category>> {
        TODO("Not yet implemented")
    }


    override suspend fun getCategory(id: Long) = db.categoryDao().get(id)
    override suspend fun getChildrenCategories(pid: Long): Flow<List<Category>> = db.categoryDao().getChildrenCategory(pid)
    override suspend fun getCategories(depth: Int): Flow<List<Category>> = db.categoryDao().getCategories(depth)
}