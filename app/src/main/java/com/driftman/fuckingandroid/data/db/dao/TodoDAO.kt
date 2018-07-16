package com.driftman.fuckingandroid.data.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.driftman.fuckingandroid.data.entity.Todo
import io.reactivex.Flowable

/**
 * Created by abk on 16/07/2018.
 */
@Dao
interface TodoDAO {

    @Query("SELECT * FROM Todos;")
    fun read(): Flowable<List<Todo>>;

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(todos: ArrayList<Todo>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(todo: Todo)
}