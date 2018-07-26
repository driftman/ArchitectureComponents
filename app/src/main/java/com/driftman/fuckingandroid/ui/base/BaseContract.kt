package com.driftman.fuckingandroid.ui.base

import android.content.Context

/**
 * Created by abk on 26/07/2018.
 */
class BaseContract {

    interface IBasePresenter<V: IBaseView> {
        fun onAttach(view: V?)
        fun onDetach()
        fun onViewInitialized()
    }

    interface IBaseView {

    }
}