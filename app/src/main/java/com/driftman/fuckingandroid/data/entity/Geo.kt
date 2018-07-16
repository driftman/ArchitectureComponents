package com.driftman.fuckingandroid.data.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by abk on 15/07/2018.
 */
data class Geo(
        val lng: String,
        val lat: String
) {
}