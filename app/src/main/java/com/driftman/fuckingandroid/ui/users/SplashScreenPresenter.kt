package com.driftman.fuckingandroid.ui.users;

import android.util.Log
import com.driftman.fuckingandroid.repository.SynchronizationService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by abk on 25/07/2018.
 */

class SplashScreenPresenter<V: SplashScreenContract.IViewSplashScreen> (var synchronizationService: SynchronizationService)
    : SplashScreenContract.ISplashScreenPresenter<V> {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()
    lateinit var view: V

    override fun onAttach(view: V) {
        Log.d(TAG, "onAttach()")
        this.view = view
    }

    override fun onDetach() {
        Log.d(TAG, "onDetach()")
    }

    override fun onViewInitialized() {
        Log.d(TAG, "onViewInitialized()")
        view.initialState()
        val disposable = Observable.interval(30, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe {
                    l -> sync(l)
                }
        compositeDisposable.add(disposable)
    }

    private fun sync(iteration: Long) {
        val synchronizationDisposable: Disposable = SynchronizationService(view.context())
                .sync()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { view.addLogAndScrollToTop(it.status) },
                        { it -> println("Error: ${it.message}")},
                        {
                            view.addLogAndScrollToTop("Synchronization ended successfully.")
                            view.finishedState()
                            view.setSyncNb(iteration + 1)
                        })

        compositeDisposable.add(synchronizationDisposable)
    }

    companion object {
        val TAG = "SplashScreenPresenter"
    }

}
