package com.driftman.fuckingandroid.di

import android.content.Context
import com.driftman.fuckingandroid.data.db.UserDAO
import com.driftman.fuckingandroid.data.db.UserDatabase

object Injection {

    fun provideUserDatasource(context: Context): UserDAO {
        val userDatabase: UserDatabase = UserDatabase.getInstance(context)
        return userDatabase.userDao()
    }

}