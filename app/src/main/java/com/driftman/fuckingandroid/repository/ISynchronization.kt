package com.driftman.fuckingandroid.repository

import io.reactivex.Observable
import java.util.*

/**
 * Created by abk on 16/07/2018.
 */

interface ISynchronization {

    fun sync(): Observable<SynchronizationResult>

}