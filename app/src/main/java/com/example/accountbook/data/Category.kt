package com.example.accountbook.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_Category")
data class Category(
    @PrimaryKey(autoGenerate = true) val uid: Long = 0,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "idParent") var pid: Long,
    @ColumnInfo(name = "depth") var depth: Int = -1
)
