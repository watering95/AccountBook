package com.example.accountbook

import android.app.Application
import androidx.annotation.WorkerThread
import com.example.accountbook.data.*
import kotlinx.coroutines.CoroutineScope

class AppRepository(val application: Application, private val scope: CoroutineScope) {
    var db = AppDatabase.getDatabase(application, scope)
    private var groupDao = db.groupDao()
    private var accountDao = db.accountDao()
    private var cardDao = db.cardDao()
    private var valueDao = db.valueDao()
    private var transactionDao = db.transactionDao()
    private var categoryDao = db.categoryDao()
    private var paymentDao = db.paymentDao()

    fun close() {
        if(db.isOpen) db.openHelper.close()
    }

    @WorkerThread
    fun <T> insert(t: T) {
        if(t is Group) groupDao.insert(t)
        if(t is Account) accountDao.insert(t)
        if(t is Card) cardDao.insert(t)
        if(t is Value) valueDao.insert(t)
        if(t is Transaction) transactionDao.insert(t)
        if(t is Category) categoryDao.insert(t)
        if(t is Payment) paymentDao.insert(t)
    }

    @WorkerThread
    fun <T> update(t: T) {
        if(t is Group) groupDao.update(t)
        if(t is Account) accountDao.update(t)
        if(t is Card) cardDao.update(t)
        if(t is Value) valueDao.update(t)
        if(t is Transaction) transactionDao.update(t)
        if(t is Category) categoryDao.update(t)
        if(t is Payment) paymentDao.update(t)
    }

    @WorkerThread
    fun <T> delete(t: T) {
        if(t is Group) groupDao.delete(t)
        if(t is Account) accountDao.delete(t)
        if(t is Card) cardDao.delete(t)
        if(t is Value) valueDao.delete(t)
        if(t is Transaction) transactionDao.delete(t)
        if(t is Category) categoryDao.delete(t)
        if(t is Payment) paymentDao.delete(t)
    }
}