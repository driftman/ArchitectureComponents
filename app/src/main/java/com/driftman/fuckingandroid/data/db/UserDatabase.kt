package com.driftman.fuckingandroid.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context


@Database(entities = [User::class], version = 1)
abstract class UserDatabase : RoomDatabase() {


    abstract fun userDao(): UserDAO

    companion object {

        @Volatile private var INSTANCE: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase =
                INSTANCE ?: synchronized(this) {
                  buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        UserDatabase::class.java, "Sample.db")
                        .build()

    }
}


