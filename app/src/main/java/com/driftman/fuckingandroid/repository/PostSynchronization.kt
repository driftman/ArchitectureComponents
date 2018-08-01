package com.driftman.fuckingandroid.repository

import android.util.Log
import com.driftman.fuckingandroid.data.db.dao.PostDAO
import com.driftman.fuckingandroid.data.entity.Post
import com.driftman.fuckingandroid.data.network.service.PostService
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

/**
 * Created by abk on 16/07/2018.
 */
class PostSynchronization(
        val postService: PostService,
        val postDAO: PostDAO): ISynchronization {


    override fun sync(): Observable<SynchronizationResult> {
        return postService.get()
                .subscribeOn(Schedulers.io())
                .flatMap(SaveToLocalDB(postDAO))
    }

    private class SaveToLocalDB(val postDAO: PostDAO): Function<ArrayList<Post>, Observable<SynchronizationResult>> {


        override fun apply(t: ArrayList<Post>): Observable<SynchronizationResult> {
            return Observable.create<SynchronizationResult> {
                e: ObservableEmitter<SynchronizationResult> ->
                e.onNext(SynchronizationResult(SynchronizationService.POST_SERVICE, "Posts successfully fetched from network."))
                e.onNext(SynchronizationResult(SynchronizationService.POST_SERVICE, "Saving posts in local DB ..."))
                postDAO.create(t)
                e.onNext(SynchronizationResult(SynchronizationService.POST_SERVICE, "Posts saved in local DB."))
                e.onComplete()
            }
        }
    }

}