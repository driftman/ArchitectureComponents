package com.driftman.fuckingandroid.data.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by abk on 15/07/2018.
 */

@Entity(tableName = "Addresses")
data class Address(
        @PrimaryKey
        @ColumnInfo()
        val street: String,
        @ColumnInfo()
        val suite: String,
        @ColumnInfo()
        val city: String,
        @ColumnInfo()
        val zipCode: String,
        @ColumnInfo()
        val geo: Geo
) {
}