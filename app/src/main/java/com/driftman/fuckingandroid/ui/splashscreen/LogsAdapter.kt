package com.driftman.fuckingandroid.ui.splashscreen

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.driftman.fuckingandroid.R
import kotlinx.android.synthetic.main.log_item.view.*
import java.util.ArrayList

class LogsAdapter(var logs: ArrayList<String>) : RecyclerView.Adapter<LogsAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.log_item, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return logs.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val log = logs.get(position)
        holder.logTextView.text = log
    }

    fun add(log: String) {
        this.logs.add(log)
        notifyDataSetChanged()
    }

    class UserViewHolder(v: View) :
            RecyclerView.ViewHolder(v) {
        val logTextView = v.log_text_view
    }

}