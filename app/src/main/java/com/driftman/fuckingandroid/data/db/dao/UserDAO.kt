package com.driftman.fuckingandroid.data.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.driftman.fuckingandroid.data.entity.User
import io.reactivex.Flowable


@Dao
interface UserDAO  {

    @Query("SELECT * FROM Users;")
    fun read() : Flowable<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(users: List<User>)
}