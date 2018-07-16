package com.driftman.fuckingandroid.ui.users

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.driftman.fuckingandroid.R
import com.driftman.fuckingandroid.repository.SynchronizationService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.splash_screen.*

class SplashScreenActivity : AppCompatActivity() {

    lateinit var logsRecyclerView: RecyclerView
    lateinit var llm: LinearLayoutManager;
    lateinit var logsAdapter: LogsAdapter;
    val compositeDisposable: CompositeDisposable = CompositeDisposable()
    val logs: ArrayList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        initialState()

        logsRecyclerView = logs_recycler_view
        llm = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        logs.add("Fetching users from network ...")
        logs.add("Fetching todos from network ...")
        logs.add("Fetching posts from network ...")
        logsAdapter = LogsAdapter(logs)
        logsRecyclerView.layoutManager = llm
        logsRecyclerView.adapter = logsAdapter
        val synchronizationDisposable: Disposable  = SynchronizationService(this)
                .sync()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { addLogAndScrollToTop(it.status) },
                        { it -> println("Error: ${it.message}")},
                        {
                            addLogAndScrollToTop("Synchronizarion ended successfully.")
                            finishedState()
                        })

        compositeDisposable.add(synchronizationDisposable)
    }

    fun addLogAndScrollToTop(log: String) {
        logsAdapter.add(log)
        logsRecyclerView.scrollToPosition(logsAdapter.itemCount - 1)
    }

    fun finishedState() {
        loading_message_text_view.text = "DONE"
        progress_bar.visibility = View.GONE
        done_image_view.visibility = View.VISIBLE
    }

    fun initialState() {
        loading_message_text_view.text = "Loading ..."
        progress_bar.visibility = View.VISIBLE
        done_image_view.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        if(!compositeDisposable.isDisposed)
            compositeDisposable.dispose()
    }

}