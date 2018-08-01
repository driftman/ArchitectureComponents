package com.driftman.fuckingandroid.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.driftman.fuckingandroid.R
import com.driftman.fuckingandroid.log.CustomLog
import com.driftman.fuckingandroid.repository.SynchronizationService
import com.driftman.fuckingandroid.ui.base.BaseActivity
import com.driftman.fuckingandroid.ui.users.UsersActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_users.*
import kotlinx.android.synthetic.main.splash_screen.*
import java.util.concurrent.TimeUnit

class SplashScreenActivity : BaseActivity(), SplashScreenContract.IViewSplashScreen {

    lateinit var logsAdapter: LogsAdapter;
    lateinit var presenter: SplashScreenPresenter<SplashScreenActivity>
    lateinit var customLog: CustomLog

    val logs: ArrayList<String> = ArrayList()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        customLog = CustomLog(this, "SplashScreenActivity")
        customLog.d("onCreate()")

        presenter = SplashScreenPresenter(this, SynchronizationService(this))

        val llm = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        logs.add("Fetching users from network ...")
        logs.add("Fetching todos from network ...")
        logs.add("Fetching posts from network ...")

        logsAdapter = LogsAdapter(logs)

        logs_recycler_view.layoutManager = llm
        logs_recycler_view.adapter = logsAdapter

        presenter.onAttach(this)
        presenter.onViewInitialized()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun addLogAndScrollToTop(log: String) {
        logsAdapter.add(log)
        logs_recycler_view.scrollToPosition(logsAdapter.itemCount - 1)
    }

    override fun finishedState() {

        loading_message_text_view.text = "DONE"
        progress_bar.visibility = View.GONE
        done_image_view.visibility = View.VISIBLE

        Observable.interval(5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .firstElement()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    customLog.i("All data successfully downloaded.")
                    customLog.i("Launching the UsersActivity.")
                    val intent: Intent = Intent(this, UsersActivity::class.java)
                    startActivity(intent)
                    finish()
                }

    }

    override fun initialState() {
        loading_message_text_view.text = "Loading ..."
        progress_bar.visibility = View.VISIBLE
        done_image_view.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
    }

}