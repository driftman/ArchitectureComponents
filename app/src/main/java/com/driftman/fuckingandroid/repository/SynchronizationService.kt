package com.driftman.fuckingandroid.repository

import android.content.Context
import com.driftman.fuckingandroid.data.db.dao.PostDAO
import com.driftman.fuckingandroid.data.db.dao.TodoDAO
import com.driftman.fuckingandroid.data.db.dao.UserDAO
import com.driftman.fuckingandroid.data.network.service.PostService
import com.driftman.fuckingandroid.data.network.service.TodoService
import com.driftman.fuckingandroid.data.network.service.UserService
import com.driftman.fuckingandroid.di.Injection
import io.reactivex.Observable

/**
 * Created by abk on 16/07/2018.
 */

class SynchronizationService(val context: Context): ISynchronization {

    val userService: UserService = Injection().provideUserService()
    val todoService: TodoService = Injection().provideTodoService()
    val postService: PostService = Injection().providePostService()

    val userDAO: UserDAO = Injection().provideUserDAO(context)
    val todoDAO: TodoDAO = Injection().provideTodoDAO(context)
    val postDAO: PostDAO = Injection().providePostDAO(context)

    val userSynchronization: ISynchronization = UserSynchronization(userService, userDAO)
    val todoSynchronization: ISynchronization = TodoSynchronization(todoService, todoDAO)
    val postSynchronization: ISynchronization = PostSynchronization(postService, postDAO)

    override fun sync(): Observable<SynchronizationResult> {
        return Observable.mergeDelayError(
            userSynchronization.sync(),
            todoSynchronization.sync(),
            postSynchronization.sync())
    }

    companion object {
        val USER_SERVICE: String = "USER_SERVICE"
        val POST_SERVICE: String = "POST_SERVICE"
        val TODO_SERVICE: String = "TODO_SERVICE"
    }

}