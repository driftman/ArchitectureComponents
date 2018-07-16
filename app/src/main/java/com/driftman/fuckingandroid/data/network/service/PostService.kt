package com.driftman.fuckingandroid.data.network.service

import com.driftman.fuckingandroid.data.entity.Post
import com.driftman.fuckingandroid.data.network.api.INetworkCall
import com.driftman.fuckingandroid.data.network.api.PostAPI
import com.driftman.fuckingandroid.data.network.response.NetworkResponse
import io.reactivex.Observable
import java.util.ArrayList


/**
 * Created by abk on 15/07/2018.
 */

class PostService(val postAPI: PostAPI): INetworkCall<Post, NetworkResponse> {

    override fun get(): Observable<ArrayList<Post>> = postAPI.get()

    override fun post(p: Post): Observable<NetworkResponse> = postAPI.post(p)

    override fun post(p: ArrayList<Post>): Observable<NetworkResponse> = postAPI.post(p)

    override fun put(p: Post): Observable<NetworkResponse>  = postAPI.put(p)

    override fun put(p: ArrayList<Post>): Observable<NetworkResponse> = postAPI.put(p)

    override fun delete(p: Post): Observable<NetworkResponse> = postAPI.delete(p)

    override fun delete(p: ArrayList<Post>): Observable<NetworkResponse> = postAPI.delete(p)
}