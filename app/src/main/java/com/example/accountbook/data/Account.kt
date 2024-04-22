package com.example.accountbook.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "tbl_Account",
    foreignKeys = [
        ForeignKey(entity = Group::class, parentColumns = ["uid"], childColumns = ["idGroup"], onDelete = ForeignKey.CASCADE)
    ]
)
data class Account(
    @PrimaryKey(autoGenerate = true) val uid: Long = 0,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "company") var company: String,
    @ColumnInfo(name = "number") var number: String,
    @ColumnInfo(name = "idGroup") var idGroup: Long,
    @ColumnInfo(name = "balance") var balance: Double,
    @ColumnInfo(name = "use") var use: Boolean
)
