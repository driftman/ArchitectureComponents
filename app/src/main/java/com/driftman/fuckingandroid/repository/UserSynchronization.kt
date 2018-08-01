package com.driftman.fuckingandroid.repository

import com.driftman.fuckingandroid.data.db.dao.UserDAO
import com.driftman.fuckingandroid.data.entity.User
import com.driftman.fuckingandroid.data.network.service.UserService
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

/**
 * Created by abk on 16/07/2018.
 */
class UserSynchronization(
        val userService: UserService,
        val userDAO: UserDAO): ISynchronization {


    override fun sync(): Observable<SynchronizationResult> {
        return userService
                .get()
                .subscribeOn(Schedulers.io())
                .flatMap(SaveToLocalDB(userDAO))
    }

    private class SaveToLocalDB(val userDAO: UserDAO): Function<ArrayList<User>, Observable<SynchronizationResult>> {


        override fun apply(t: ArrayList<User>): Observable<SynchronizationResult> {
            return Observable.create<SynchronizationResult> {
                e: ObservableEmitter<SynchronizationResult> ->
                e.onNext(SynchronizationResult(SynchronizationService.USER_SERVICE, "Users successfully fetched from network."))
                e.onNext(SynchronizationResult(SynchronizationService.USER_SERVICE, "Saving users in local DB ..."))
                try {
                    userDAO.create(t)
                    e.onNext(SynchronizationResult(SynchronizationService.USER_SERVICE, "Users saved in local DB."))
                } catch(ex: Exception) {
                    e.onError(ex)
                } finally {
                    e.onComplete()
                }
            }
        }
    }
}