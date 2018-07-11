package com.driftman.fuckingandroid.ui.users

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.driftman.fuckingandroid.R
import com.driftman.fuckingandroid.data.db.User
import kotlinx.android.synthetic.main.activity_users.*

class UsersActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var users: Array<User>
    lateinit var llm: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        populateUsers()
        setContentView(R.layout.activity_users)
        recyclerView = recycler_view
        llm = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = llm
        recyclerView.adapter = UsersAdapter(users)
    }

    private fun populateUsers() {
        users = Array(10, { it -> User(
                id = it.toString(),
                name = "name $it",
                age = Integer(it + 20))
        })
    }
}
