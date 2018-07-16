package com.driftman.fuckingandroid.data.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.driftman.fuckingandroid.data.entity.Post
import io.reactivex.Flowable

/**
 * Created by abk on 16/07/2018.
 */
@Dao
interface PostDAO {

    @Query("SELECT * FROM Posts;")
    fun read(): Flowable<List<Post>>;

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(posts: List<Post>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(post: Post)
}