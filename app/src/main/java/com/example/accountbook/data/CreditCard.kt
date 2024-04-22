package com.example.accountbook.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_CreditCard")
data class CreditCard(
    @PrimaryKey(autoGenerate = true) val uid: Long = 0,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "company") var company: String,
    @ColumnInfo(name = "number") var number: String,
    @ColumnInfo(name = "idAccount") var idAccount: Long,
    @ColumnInfo(name = "use") val use: Boolean
)
