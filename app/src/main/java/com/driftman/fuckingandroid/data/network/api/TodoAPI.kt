package com.driftman.fuckingandroid.data.network.api

import com.driftman.fuckingandroid.data.entity.Todo
import com.driftman.fuckingandroid.data.network.response.NetworkResponse
import io.reactivex.Observable
import retrofit2.http.*
/**
 * Created by abk on 15/07/2018.
 */
interface TodoAPI {

    @GET("/todos")
    fun get(): Observable<ArrayList<Todo>>

    @POST("/todos")
    @FormUrlEncoded
    fun post(p: Todo): Observable<NetworkResponse>

    @POST("/todos")
    @FormUrlEncoded
    fun post(todos: ArrayList<Todo>): Observable<NetworkResponse>

    @PUT("/todos")
    @FormUrlEncoded
    fun put(p: Todo): Observable<NetworkResponse>

    @PUT("/todos")
    @FormUrlEncoded
    fun put(todos: ArrayList<Todo>): Observable<NetworkResponse>

    @DELETE("/todos")
    @FormUrlEncoded
    fun delete(p: Todo): Observable<NetworkResponse>

    @DELETE("/todos")
    @FormUrlEncoded
    fun delete(todos: ArrayList<Todo>): Observable<NetworkResponse>
}