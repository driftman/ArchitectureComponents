package com.driftman.fuckingandroid.ui.users

import android.arch.lifecycle.ViewModel
import com.driftman.fuckingandroid.data.db.User
import com.driftman.fuckingandroid.data.db.UserDAO
import io.reactivex.Completable
import io.reactivex.Flowable

class UserViewModel(private val datasource: UserDAO) : ViewModel() {


    fun read(): Flowable<List<User>> {
        return datasource.read()
    }


    fun update(user: User): Completable {
        return Completable.fromAction {
            val user: User = User("1" ,"Soufiane ELBAZ", Integer("26"))
            datasource.create(user)
        }
    }
    
}