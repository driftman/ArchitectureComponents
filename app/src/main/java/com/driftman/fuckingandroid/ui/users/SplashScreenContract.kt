package com.driftman.fuckingandroid.ui.users

import android.content.Context

/**
 * Created by abk on 25/07/2018.
 */

class SplashScreenContract {

    interface ISplashScreenPresenter<V : IViewSplashScreen> {
        fun onAttach(view: V)
        fun onDetach()
        fun onViewInitialized()
    }

    interface IViewSplashScreen {
        fun context(): Context
        fun setSyncNb(nb: Long?)
        fun addLogAndScrollToTop(log: String)
        fun finishedState()
        fun initialState()
    }
}
