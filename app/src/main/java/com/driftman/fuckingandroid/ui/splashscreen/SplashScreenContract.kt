package com.driftman.fuckingandroid.ui.splashscreen

import android.content.Context
import com.driftman.fuckingandroid.ui.base.BaseContract

/**
 * Created by abk on 25/07/2018.
 */

class SplashScreenContract {

    interface ISplashScreenPresenter<V : IViewSplashScreen>: BaseContract.IBasePresenter<V> {

    }

    interface IViewSplashScreen : BaseContract.IBaseView {
        fun addLogAndScrollToTop(log: String)
        fun finishedState()
        fun initialState()
    }
}
