package com.driftman.fuckingandroid.ui.users

import com.driftman.fuckingandroid.data.db.dao.UserDAO
import com.driftman.fuckingandroid.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by abk on 26/07/2018.
 */
class UsersPresenter<V: UsersContact.IUsersView>(var userDAO: UserDAO): BasePresenter<V>(),
        UsersContact.IUsersPresenter<V> {

    override fun search(value: String?) {
        compositeDisposable.add(
                userDAO.searchByName("%${value}%")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            users -> v?.updateUsers(users)
                        }))
    }

    override fun onViewInitialized() {
        super.onViewInitialized()
        compositeDisposable.add(
                userDAO.read()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    users -> v?.updateUsers(users)
                }))
    }

}