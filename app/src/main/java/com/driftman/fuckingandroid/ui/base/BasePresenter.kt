package com.driftman.fuckingandroid.ui.base

import android.util.Log
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by abk on 26/07/2018.
 */
abstract class BasePresenter<V: BaseContract.IBaseView>: BaseContract.IBasePresenter<V> {

    var compositeDisposable: CompositeDisposable = CompositeDisposable()
    var v: V? = null

    override fun onAttach(view: V?) {
        Log.d(TAG, "onAttach()")
        v = view
    }

    override fun onDetach() {
        Log.d(TAG, "onDetach()")
        compositeDisposable.dispose()
        v = null
    }

    override fun onViewInitialized() {
        Log.d(TAG, "onViewInitialized()")
    }

    companion object {
        val TAG = "BasePresenter"
    }
}