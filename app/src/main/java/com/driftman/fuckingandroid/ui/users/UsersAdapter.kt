package com.driftman.fuckingandroid.ui.users

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.driftman.fuckingandroid.data.db.User
import com.driftman.fuckingandroid.R
import kotlinx.android.synthetic.main.user_item.view.*

class UsersAdapter(private val users: Array<User>) : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return UserViewHolder(v)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users.get(position)
        holder?.nom?.text = user.name
        holder?.age?.text = user.age.toString()
    }

    class UserViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val nom = v.name
        val age = v.age
    }
}