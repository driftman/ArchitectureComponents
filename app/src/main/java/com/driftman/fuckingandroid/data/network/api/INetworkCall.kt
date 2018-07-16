package com.driftman.fuckingandroid.data.network.api

import io.reactivex.Observable
import java.util.ArrayList

/**
 * Created by abk on 16/07/2018.
 */
interface INetworkCall <E, R> {

    fun get(): Observable<ArrayList<E>>;
    fun post(t: E): Observable<R>
    fun post(t: ArrayList<E>): Observable<R>
    fun put(t: E): Observable<R>
    fun put(t: ArrayList<E>): Observable<R>
    fun delete(t: E): Observable<R>
    fun delete(t: ArrayList<E>): Observable<R>

}