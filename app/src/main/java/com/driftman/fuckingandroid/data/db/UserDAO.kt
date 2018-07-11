package com.driftman.fuckingandroid.data.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Flowable
import java.lang.reflect.Array


@Dao
interface UserDAO  {

    @Query("SELECT * FROM users WHERE Users.name like :firstName;")
    fun getUserByName(firstName: String): Flowable<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(user: User)

    @Query("SELECT * FROM Users;")
    fun read() : Flowable<List<User>>

}