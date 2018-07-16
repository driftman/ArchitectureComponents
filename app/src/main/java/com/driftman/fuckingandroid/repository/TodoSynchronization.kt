package com.driftman.fuckingandroid.repository

import android.util.Log
import com.driftman.fuckingandroid.data.db.dao.TodoDAO
import com.driftman.fuckingandroid.data.entity.Todo
import com.driftman.fuckingandroid.data.network.service.TodoService
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

/**
 * Created by abk on 16/07/2018.
 */
class TodoSynchronization(
        val todoService: TodoService,
        val todoDAO: TodoDAO): ISynchronization {

    override fun sync(): Observable<SynchronizationResult> {
        return todoService.get()
                .subscribeOn(Schedulers.io())
                .flatMap(SaveToLocalDB(todoDAO))
    }

    private class SaveToLocalDB(val todoDAO: TodoDAO): Function<ArrayList<Todo>, Observable<SynchronizationResult>> {


        override fun apply(t: ArrayList<Todo>): Observable<SynchronizationResult> {
            return Observable.create<SynchronizationResult> {
                e: ObservableEmitter<SynchronizationResult> ->
                Thread.sleep(1500)
                e.onNext(SynchronizationResult(SynchronizationService.TODO_SERVICE, "Todos successfully fetched from network."))
                e.onNext(SynchronizationResult(SynchronizationService.TODO_SERVICE, "Saving todos in local DB ..."))
                Thread.sleep(2000)
                todoDAO.create(t)
                e.onNext(SynchronizationResult(SynchronizationService.TODO_SERVICE, "Todos saved in local DB."))
                e.onComplete()
            }.subscribeOn(Schedulers.io())
        }
    }
}
