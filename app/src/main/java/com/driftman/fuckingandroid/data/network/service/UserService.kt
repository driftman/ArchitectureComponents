package com.driftman.fuckingandroid.data.network.service;

import com.driftman.fuckingandroid.data.entity.User
import com.driftman.fuckingandroid.data.network.api.INetworkCall
import com.driftman.fuckingandroid.data.network.api.UserAPI;
import com.driftman.fuckingandroid.data.network.response.NetworkResponse

import io.reactivex.Observable;
import java.util.ArrayList


/**
 * Created by abk on 15/07/2018.
 */
class UserService(val userAPI: UserAPI): INetworkCall<User, NetworkResponse> {

    override fun get(): Observable<ArrayList<User>> = userAPI.get()

    override fun post(u: User): Observable<NetworkResponse> = userAPI.post(u)

    override fun post(u: ArrayList<User>): Observable<NetworkResponse> = userAPI.post(u)

    override fun put(u: User): Observable<NetworkResponse> = userAPI.put(u)

    override fun put(u: ArrayList<User>): Observable<NetworkResponse> = userAPI.put(u)

    override fun delete(u: User): Observable<NetworkResponse> = userAPI.delete(u)

    override fun delete(u: ArrayList<User>): Observable<NetworkResponse> = userAPI.delete(u)
}