package com.driftman.fuckingandroid.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.driftman.fuckingandroid.data.db.dao.PostDAO
import com.driftman.fuckingandroid.data.db.dao.TodoDAO
import com.driftman.fuckingandroid.data.db.dao.UserDAO
import com.driftman.fuckingandroid.data.entity.*


@Database(
    entities =
        [   User::class,
            Post::class,
            Todo::class
        ],
    version = 1)
abstract class ApplicationDatabase : RoomDatabase() {


    abstract fun userDAO(): UserDAO
    abstract fun todoDAO(): TodoDAO
    abstract fun postDAO(): PostDAO

    companion object {

        @Volatile private var INSTANCE: ApplicationDatabase? = null

        fun getInstance(context: Context): ApplicationDatabase =
                INSTANCE ?: synchronized(this) {
                  buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        ApplicationDatabase::class.java, "FuckingAndroid.db")
                        .build()

    }
}


