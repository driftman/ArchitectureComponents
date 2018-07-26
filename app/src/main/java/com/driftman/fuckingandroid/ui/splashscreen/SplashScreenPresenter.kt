package com.driftman.fuckingandroid.ui.splashscreen;

import android.content.Context
import android.util.Log
import com.driftman.fuckingandroid.repository.SynchronizationService
import com.driftman.fuckingandroid.ui.base.BasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by abk on 25/07/2018.
 */

class SplashScreenPresenter<V: SplashScreenContract.IViewSplashScreen>
(var context: Context, var synchronizationService: SynchronizationService)
    : SplashScreenContract.ISplashScreenPresenter<V>, BasePresenter<V>() {



    override fun onViewInitialized() {
        super.onViewInitialized()
        v?.initialState()
        val disposable =
                Observable.interval(0, 2, TimeUnit.SECONDS)
                .takeWhile { it < 5 }
                .subscribe {
                    it -> sync(it.toInt())
                }
        compositeDisposable.add(disposable)
    }

    private fun sync(iteration: Int) {
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
                            v?.setSyncNb(iteration + 1)
                        })

        compositeDisposable.add(synchronizationDisposable)
    }

    companion object {
        val TAG = "SplashScreenPresenter"
    }

}
