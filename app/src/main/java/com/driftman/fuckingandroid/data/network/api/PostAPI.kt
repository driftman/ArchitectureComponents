package com.driftman.fuckingandroid.data.network.api

import com.driftman.fuckingandroid.data.entity.Post
import com.driftman.fuckingandroid.data.network.response.NetworkResponse
import io.reactivex.Observable
import retrofit2.http.*
/**
 * Created by abk on 15/07/2018.
 */

interface PostAPI {

    @GET("/posts")
    fun get(): Observable<ArrayList<Post>>

    @POST("/posts")
    @FormUrlEncoded
    fun post(p: Post): Observable<NetworkResponse>

    @POST("/posts")
    @FormUrlEncoded
    fun post(posts: ArrayList<Post>): Observable<NetworkResponse>

    @PUT("/posts")
    @FormUrlEncoded
    fun put(p: Post): Observable<NetworkResponse>

    @PUT("/posts")
    @FormUrlEncoded
    fun put(posts: ArrayList<Post>): Observable<NetworkResponse>

    @DELETE("/posts")
    @FormUrlEncoded
    fun delete(p: Post): Observable<NetworkResponse>

    @DELETE("/posts")
    @FormUrlEncoded
    fun delete(posts: ArrayList<Post>): Observable<NetworkResponse>

}