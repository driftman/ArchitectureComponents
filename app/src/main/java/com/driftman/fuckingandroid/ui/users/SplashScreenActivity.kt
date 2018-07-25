package com.driftman.fuckingandroid.ui.users

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.driftman.fuckingandroid.R
import com.driftman.fuckingandroid.repository.SynchronizationService
import kotlinx.android.synthetic.main.splash_screen.*

class SplashScreenActivity : AppCompatActivity(), SplashScreenContract.IViewSplashScreen {

    lateinit var llm: LinearLayoutManager;
    lateinit var logsAdapter: LogsAdapter;
    lateinit var  presenter: SplashScreenPresenter<SplashScreenActivity>
    val logs: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        presenter = SplashScreenPresenter(SynchronizationService(this))

        llm = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        logs.add("Fetching users from network ...")
        logs.add("Fetching todos from network ...")
        logs.add("Fetching posts from network ...")

        logsAdapter = LogsAdapter(logs)

        logs_recycler_view.layoutManager = llm
        logs_recycler_view.adapter = logsAdapter

        presenter.onAttach(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.onViewInitialized()
    }

    override fun addLogAndScrollToTop(log: String) {
        logsAdapter.add(log)
        logs_recycler_view.scrollToPosition(logsAdapter.itemCount - 1)
    }

    override fun finishedState() {
        loading_message_text_view.text = "DONE"
        progress_bar.visibility = View.GONE
        done_image_view.visibility = View.VISIBLE
    }

    override fun initialState() {
        loading_message_text_view.text = "Loading ..."
        progress_bar.visibility = View.VISIBLE
        done_image_view.visibility = View.GONE
    }

    override fun context(): Context = applicationContext

    override fun setSyncNb(nb: Long?) {
        sync_nb.text = "Sync: ${nb}"
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
    }

}