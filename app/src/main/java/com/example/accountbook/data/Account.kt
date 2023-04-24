package com.example.accountbook.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_Account")
data class Account(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "company") var company: String,
    @ColumnInfo(name = "number") var number: String,
    @ColumnInfo(name = "idGroup") var idGroup: Int?,
    @ColumnInfo(name = "idPayment") var idPayment: Int,
    @ColumnInfo(name = "use") var use: Boolean
)
