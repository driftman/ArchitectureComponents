package com.driftman.fuckingandroid.ui.users

import com.driftman.fuckingandroid.data.entity.User
import com.driftman.fuckingandroid.ui.base.BaseContract

/**
 * Created by abk on 26/07/2018.
 */
class UsersContact {

    interface IUsersPresenter<V: IUsersView>: BaseContract.IBasePresenter<V> {
        fun search(value: String?);
    }

    interface IUsersView: BaseContract.IBaseView {
        fun updateUsers(users: List<User>)
        fun showLoading()
        fun hideLoading()
    }
}