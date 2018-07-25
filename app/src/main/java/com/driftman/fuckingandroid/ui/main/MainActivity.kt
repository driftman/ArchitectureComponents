package com.driftman.fuckingandroid.ui.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.driftman.fuckingandroid.R
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.AsyncSubject
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.ReplaySubject
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.runBlocking
import org.reactivestreams.Subscriber
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firstObservable = Observable.interval(200, TimeUnit.MILLISECONDS)
        val secondObservable = Observable.fromArray("one", "two", "three", "four", "five")

        val mergedObservable = Observable.mergeArray(firstObservable, secondObservable)

        mergedObservable.subscribe {
            Log.d(this.toString(), "Received: ${it}")
        }
    }

    object GenerateIntNumber {

        var number: Int = 0

        get() {
            field += 1
            return field
        }
    }

    data class Prepared(val id: Int) {
        init {
            android.util.Log.d("MainActivity", "Prepared ${id}.")
        }
    }

    data class Next(val id: Int) {
        init {
            android.util.Log.d("MainActivity", "Next ${id}.")
        }
    }

    class CustomObserver(val observer: String): Observer<Long> {

        override fun onError(e: Throwable) {
            android.util.Log.d("MainActivity", "Subscriber(${observer}) : Error.")        }

        override fun onComplete() {
            android.util.Log.d("MainActivity", "Subscriber(${observer}) : Completed.")
        }

        override fun onSubscribe(d: Disposable) {
        }

        override fun onNext(t: Long) {
            android.util.Log.d("MainActivity", "Subscriber(${observer}) : ${t}.")
        }
    }
}
