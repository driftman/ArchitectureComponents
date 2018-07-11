package com.driftman.fuckingandroid.data.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "Users")
data class User(
        @PrimaryKey
        @ColumnInfo(name = "id")
        val id: String = UUID.randomUUID().toString(),
        @ColumnInfo(name = "name")
        val name: String,
        @ColumnInfo(name = "age")
        val age: Integer
)