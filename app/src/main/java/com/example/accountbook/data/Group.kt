package com.example.accountbook.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_group")
data class Group(
    @PrimaryKey(autoGenerate = true) val uid: Long = 0,
    @ColumnInfo(name = "name") var name: String
)
