package com.driftman.fuckingandroid.data.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.databinding.BaseObservable
import android.databinding.Bindable

/**
 * Created by abk on 15/07/2018.
 */

@Entity(tableName = "Users")
data class User(
        @PrimaryKey
        @ColumnInfo()
        val id: Int,
        @ColumnInfo()
        @Bindable
        val name: String,
        @ColumnInfo()
        @Bindable
        val username: String,
        @ColumnInfo()
        @Bindable
        val phone: String,
        @ColumnInfo()
        @Bindable
        val website: String
): BaseObservable () {}