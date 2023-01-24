package com.example.accountbook

import android.app.Application
import androidx.annotation.WorkerThread
import com.example.accountbook.data.*
import kotlinx.coroutines.CoroutineScope

class AppRepository(val application: Application, private val scope: CoroutineScope) {
    var db = AppDatabase.getDatabase(application, scope)
    private var groupDao = db.groupDao()
    private var accountDao = db.accountDao()
    private var valueDao = db.valueDao()
    private var transactionDao = db.transactionDao()
    private var classADao = db.classADao()
    private var classBDao = db.classBDao()
    private var classCDao = db.classCDao()

    fun close() {
        if(db.isOpen) db.openHelper.close()
    }

    @WorkerThread
    fun <T> insert(t: T) {
        if(t is Group) groupDao.insert(t)
        if(t is Account) accountDao.insert(t)
        if(t is Value) valueDao.insert(t)
        if(t is Transaction) transactionDao.insert(t)
        if(t is ClassA) classADao.insert(t)
        if(t is ClassB) classBDao.insert(t)
        if(t is ClassC) classCDao.insert(t)
    }

    @WorkerThread
    fun <T> update(t: T) {
        if(t is Group) groupDao.update(t)
        if(t is Account) accountDao.update(t)
        if(t is Value) valueDao.update(t)
        if(t is Transaction) transactionDao.update(t)
        if(t is ClassA) classADao.update(t)
        if(t is ClassB) classBDao.update(t)
        if(t is ClassC) classCDao.update(t)
    }

    @WorkerThread
    fun <T> delete(t: T) {
        if(t is Group) groupDao.delete(t)
        if(t is Account) accountDao.delete(t)
        if(t is Value) valueDao.delete(t)
        if(t is Transaction) transactionDao.delete(t)
        if(t is ClassA) classADao.delete(t)
        if(t is ClassB) classBDao.delete(t)
        if(t is ClassC) classCDao.delete(t)
    }
}