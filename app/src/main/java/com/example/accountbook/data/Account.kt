package com.example.accountbook.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_Account")
data class Account(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "company") val company: String,
    @ColumnInfo(name = "number") val number: String,
    @ColumnInfo(name = "idGroup") val idGroup: Int,
    @ColumnInfo(name = "idPayment") val idPayment: Int,
    @ColumnInfo(name = "use") val use: Boolean
)
