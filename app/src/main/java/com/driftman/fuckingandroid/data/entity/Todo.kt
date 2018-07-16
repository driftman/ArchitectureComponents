package com.driftman.fuckingandroid.data.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.databinding.BaseObservable
import android.databinding.Bindable

/**
 * Created by abk on 15/07/2018.
 */
@Entity(tableName = "Todos")
data class Todo (
        @ColumnInfo()
        @Bindable
        val userId: String,
        @PrimaryKey
        @ColumnInfo()
        @Bindable
        val id: Int,
        @ColumnInfo()
        @Bindable
        val title: String,
        @ColumnInfo()
        @Bindable
        val completed: Boolean
): BaseObservable(){}