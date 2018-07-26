package com.driftman.fuckingandroid.ui.splashscreen;

import android.content.Context
import com.driftman.fuckingandroid.repository.SynchronizationService
import com.driftman.fuckingandroid.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by abk on 25/07/2018.
 */

class SplashScreenPresenter<V: SplashScreenContract.IViewSplashScreen>
(var context: Context, var synchronizationService: SynchronizationService)
    : SplashScreenContract.ISplashScreenPresenter<V>, BasePresenter<V>() {



    override fun onViewInitialized() {
        super.onViewInitialized()
        v?.initialState()
        sync()
    }

    private fun sync() {
        val synchronizationDisposable: Disposable = SynchronizationService(context)
                .sync()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { v?.addLogAndScrollToTop(it.status) },
                        { it -> println("Error: ${it.message}")},
                        {
                            v?.addLogAndScrollToTop("Synchronization ended successfully.")
                            v?.finishedState()
                        })

        compositeDisposable.add(synchronizationDisposable)
    }

    companion object {
        val TAG = "SplashScreenPresenter"
    }

}
