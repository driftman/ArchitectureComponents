package com.driftman.fuckingandroid.data.network.service

import com.driftman.fuckingandroid.data.entity.Todo
import com.driftman.fuckingandroid.data.network.api.INetworkCall
import com.driftman.fuckingandroid.data.network.api.TodoAPI
import com.driftman.fuckingandroid.data.network.response.NetworkResponse
import io.reactivex.Observable
import java.util.ArrayList

/**
 * Created by abk on 15/07/2018.
 */
class TodoService(val todoAPI: TodoAPI): INetworkCall<Todo, NetworkResponse> {

    override fun get(): Observable<ArrayList<Todo>> = todoAPI.get()

    override fun post(t: Todo): Observable<NetworkResponse> = todoAPI.post(t)

    override fun post(t: ArrayList<Todo>): Observable<NetworkResponse> = todoAPI.post(t)

    override fun put(t: Todo): Observable<NetworkResponse> = todoAPI.put(t)

    override fun put(t: ArrayList<Todo>): Observable<NetworkResponse> = todoAPI.put(t)

    override fun delete(t: Todo): Observable<NetworkResponse> = todoAPI.delete(t)

    override fun delete(t: ArrayList<Todo>): Observable<NetworkResponse> = todoAPI.delete(t)
}
