package com.example.accountbook.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_Payment")
data class Payment(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "category") val category: String
)
