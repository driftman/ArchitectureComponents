package com.driftman.fuckingandroid.ui.users

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.driftman.fuckingandroid.R
import com.driftman.fuckingandroid.data.entity.User
import kotlinx.android.synthetic.main.user_item.view.*

/**
 * Created by abk on 25/07/2018.
 */
class UsersAdapter(
        var onItemClick: (User?) -> Unit = {}
) : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    var users: List<User> = arrayListOf()

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        var user = users.get(position)
        holder.bindView(user)
    }

    fun updateUsers(users: List<User>) {
        this.users = users
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int  = users.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.user_item,parent, false)
        return UserViewHolder(view)
    }

    inner class UserViewHolder(var v: View): RecyclerView.ViewHolder(v) {
        fun bindView(user: User?) {
            with(itemView) {
                user_id.text = user?.id.toString()
                name.text = user?.name
                username.text = user?.username
                setOnClickListener { onItemClick(user) }
            }
        }
    }
}