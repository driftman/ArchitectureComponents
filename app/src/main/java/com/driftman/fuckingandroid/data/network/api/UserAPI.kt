package com.driftman.fuckingandroid.data.network.api

import com.driftman.fuckingandroid.data.entity.User
import com.driftman.fuckingandroid.data.network.response.NetworkResponse
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Created by abk on 25/07/2018.
 */
interface UserAPI {

    @GET("/users")
    fun get(): Observable<ArrayList<User>>

    @POST("/users")
    @FormUrlEncoded
    fun post(p: User): Observable<NetworkResponse>

    @POST("/users")
    @FormUrlEncoded
    fun post(users: ArrayList<User>): Observable<NetworkResponse>

    @PUT("/users")
    @FormUrlEncoded
    fun put(p: User): Observable<NetworkResponse>

    @PUT("/users")
    @FormUrlEncoded
    fun put(users: ArrayList<User>): Observable<NetworkResponse>

    @DELETE("/users")
    @FormUrlEncoded
    fun delete(p: User): Observable<NetworkResponse>

    @DELETE("/users")
    @FormUrlEncoded
    fun delete(users: ArrayList<User>): Observable<NetworkResponse>
}
