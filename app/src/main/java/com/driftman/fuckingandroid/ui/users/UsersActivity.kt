package com.driftman.fuckingandroid.ui.users

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import com.driftman.fuckingandroid.R
import com.driftman.fuckingandroid.data.entity.User
import com.driftman.fuckingandroid.di.Injection
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_users.*
import java.util.concurrent.TimeUnit

class UsersActivity : AppCompatActivity(), UsersContact.IUsersView {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    lateinit var presenter: UsersPresenter<UsersActivity>
    lateinit var adapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        adapter = UsersAdapter(
                {
                    user ->  Toast.makeText(this, "${user}", Toast.LENGTH_LONG).show()
                })

        val llm = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        users_recyclerview.layoutManager = llm
        users_recyclerview.adapter = adapter

        presenter = UsersPresenter(Injection.provideUserDAO(this))
        presenter.onAttach(this)
        presenter.onViewInitialized()

        compositeDisposable.add(
                getObservableTextWatcher(search_edit_text).toFlowable(BackpressureStrategy.LATEST)
                .debounce(300, TimeUnit.MILLISECONDS)
                .subscribe {
                    presenter.search(it)
                })
    }

    override fun updateUsers(users: List<User>) {
        adapter.updateUsers(users)
    }

    override fun showLoading() {
        Toast.makeText(this, "showLoading()", Toast.LENGTH_LONG).show()
    }

    override fun hideLoading() {
        Toast.makeText(this, "hideLoading()", Toast.LENGTH_LONG).show()
    }

    fun getObservableTextWatcher(editText: EditText): Observable<String?> {

        return Observable.create(object: ObservableOnSubscribe<String?> {
            override fun subscribe(e: ObservableEmitter<String?>) {
                editText.addTextChangedListener(object: TextWatcher {
                    override fun afterTextChanged(p0: Editable?) {}
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                    override fun onTextChanged(p: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        e.onNext(p.toString())
                    }
                })
            }

        });

    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.dispose()
    }
}
